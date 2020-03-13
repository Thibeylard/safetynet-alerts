package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Firestation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("FirestationController integration tests :")
public class FirestationControllerIT {


    // URLS
    private final static String firestationPostPutURL = "/firestation?address={address}&stationNumber={stationNumber}";
    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;
    @Autowired
    private TestRestTemplate restTemplate;
    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    private final static String firestationAddressURL = "/firestation?address={address}";

    @BeforeAll
    public static void saveData(@Value("${jsondatabase.src}") String src) throws IOException {
        data = new File(src);
        factory = new JsonFactory().setCodec(new ObjectMapper());
        JsonParser parser = factory.createParser(FirestationControllerIT.data);
        jsonFileDTO = parser.readValueAs(JsonFileDatabaseDTO.class);
    }


    @AfterAll
    public static void resetData() {
        try {
            JsonGenerator generator = factory.createGenerator(data, JsonEncoding.UTF8);
            generator.writeStartObject();
            generator.writeObjectField("persons", jsonFileDTO.getPersons());
            generator.writeObjectField("firestations", jsonFileDTO.getFirestations());
            generator.writeObjectField("medicalrecords", jsonFileDTO.getMedicalRecords());
            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("POST successful")
    void Given_dataAddedToDatabase_When_POSTFirestation_Then_correspondingDataCanBeRetrieved() throws Exception {
        Firestation firestation = FirestationFactory.createFirestation(Optional.of(Addresses.CIRCLE.getName()), Optional.empty());

        // Checking that this specific firestation doesn't already exist in database.
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, firestation.getAddress());
        assertThat(response.getBody())
                .isNull();

        // Actual request
        restTemplate.postForEntity(firestationPostPutURL, null, String.class,
                firestation.getAddress(),
                String.valueOf(firestation.getStation()));

        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, firestation.getAddress());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(firestation);
    }


    @Test
    @DisplayName("POST no partial data")
    void Given_wrongParameters_When_POSTFirestation_Then_noPartialDataSaved() throws Exception {
        Firestation firestation = FirestationFactory.createFirestation(Optional.of(Addresses.BAYMEADOWS.getName()), Optional.empty());

        // Checking that this specific firestation doesn't already exist in database.
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, firestation.getAddress());
        assertThat(response.getBody())
                .isNull();


        // Actual request
        restTemplate.postForEntity(firestationAddressURL, null, String.class,
                firestation.getAddress());
        // No stationNumber provided

        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, firestation.getAddress());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingFirestation_When_POSTFirestation_Then_personNotReplaced() throws Exception {
        Firestation existing = FirestationFactory.createFirestation(Optional.of(Addresses.FIFTHROAD.getName()), Optional.empty());
        Firestation added = FirestationFactory.createFirestation(Optional.of(existing.getAddress()), Optional.of(existing.getStation() + 2));

        // Checking that existing firestation is not in database yet
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNull();

        // Preparation request
        restTemplate.postForEntity(firestationPostPutURL, null, String.class,
                existing.getAddress(),
                String.valueOf(existing.getStation()));

        // Checking that existing firestation is actually in database
        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        // Actual request
        restTemplate.postForEntity(firestationPostPutURL, null, String.class,
                added.getAddress(),
                String.valueOf(added.getStation()));

        // Checking that existing firestation is unmodified after post
        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, added.getAddress());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEFirestation_Then_retrievedDataIsAccordinglyUpdated() throws Exception {
        // Two firestations with same address but different station number
        Firestation existing = FirestationFactory.createFirestation(Optional.of(Addresses.GOLFCOURT.getName()), Optional.empty());
        Firestation updated = FirestationFactory.createFirestation(Optional.of(existing.getAddress()), Optional.of(existing.getStation() + 2));

        // Preparation request
        restTemplate.postForEntity(firestationPostPutURL, null, String.class,
                existing.getAddress(),
                String.valueOf(existing.getStation()));

        // Checking that existing firestation is actually in database
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNotNull();

        // Firestation update
        restTemplate.put(firestationPostPutURL, String.class,
                updated.getAddress(),
                String.valueOf(updated.getStation()));

        // Checking that existing firestation is correctly modified after put
        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(updated);
    }

    @Test
    @DisplayName("UPDATE no partial data")
    void Given_noCorrespondingData_When_UPDATEFirestation_Then_noPartialDataSaved() throws Exception {
        // Two firestations with same address but different station number
        Firestation updated = FirestationFactory.createFirestation(Optional.of(Addresses.OLDYORK.getName()), Optional.empty());

        // Checking that firestation does not exist in database
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, updated.getAddress());
        assertThat(response.getBody())
                .isNull();

        // Firestation update
        restTemplate.put(firestationPostPutURL, String.class,
                updated.getAddress(),
                String.valueOf(updated.getStation()));

        // Checking that existing firestation is not modified after put
        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, updated.getAddress());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEFirestation_Then_dataCantBeRetrievedAfterDeletion() {
        // Two firestations with same address but different station number
        Firestation existing = FirestationFactory.createFirestation(Optional.of(Addresses.MECHANIC.getName()), Optional.empty());

        // Preparation request
        restTemplate.postForEntity(firestationPostPutURL, null, String.class,
                existing.getAddress(),
                String.valueOf(existing.getStation()));

        // Checking that existing firestation is actually in database
        ResponseEntity<Firestation> response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNotNull();

        // Firestation deletion
        restTemplate.delete(firestationAddressURL, existing.getAddress());

        // Checking that existing firestation is correctly modified after put
        response = restTemplate.getForEntity(firestationAddressURL, Firestation.class, existing.getAddress());
        assertThat(response.getBody())
                .isNull();
    }
}