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

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("FirestationController integration tests :")
public class FirestationControllerIT {

    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;
    private static List<Firestation> firestationsOrig;

    @Autowired
    private TestRestTemplate restTemplate;

    // URLS
    private final static String firestationPostPutURL = "/firestation?address={address}&stationNumber={stationNumber}";
    private final static String firestationAddressURL = "/firestation?address={address}";

    @BeforeAll
    public static void saveData(@Value("${jsondatabase.src}") String src) throws IOException {
        data = new File(src);
        factory = new JsonFactory().setCodec(new ObjectMapper());
        JsonParser parser = factory.createParser(FirestationControllerIT.data);
        jsonFileDTO = parser.readValueAs(JsonFileDatabaseDTO.class);
        firestationsOrig = jsonFileDTO.getFirestations();
    }


    @AfterAll
    public static void resetData() {
        try {
            JsonGenerator generator = factory.createGenerator(data, JsonEncoding.UTF8);
            generator.writeStartObject();
            generator.writeObjectField("persons", jsonFileDTO.getPersons());
            generator.writeObjectField("firestations", firestationsOrig);
            generator.writeObjectField("medicalrecords", jsonFileDTO.getMedicalRecords());
            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("POST successful")
    void Given_dataAddedToDatabase_When_POSTFirestation_Then_correspondingDataCanBeRetrieved() {
        Firestation firestation = FirestationFactory.createFirestation(Addresses.CIRCLE.getName(), null);

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
    void Given_wrongParameters_When_POSTFirestation_Then_noPartialDataSaved() {
        Firestation firestation = FirestationFactory.createFirestation(Addresses.BAYMEADOWS.getName(), null);

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
    void Given_existingFirestation_When_POSTFirestation_Then_personNotReplaced() {
        Firestation existing = FirestationFactory.createFirestation(Addresses.FIFTHROAD.getName(), null);
        Firestation added = FirestationFactory.createFirestation(existing.getAddress(), existing.getStation() + 2);

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
    void Given_dataAddedToDatabase_When_UPDATEFirestation_Then_retrievedDataIsAccordinglyUpdated() {
        // Two firestations with same address but different station number
        Firestation existing = FirestationFactory.createFirestation(Addresses.GOLFCOURT.getName(), 3);
        Firestation updated = FirestationFactory.createFirestation(existing.getAddress(), existing.getStation() + 2);

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
    void Given_noCorrespondingData_When_UPDATEFirestation_Then_noPartialDataSaved() {
        // Two firestations with same address but different station number
        Firestation updated = FirestationFactory.createFirestation(Addresses.OLDYORK.getName(), null);

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
        Firestation existing = FirestationFactory.createFirestation(Addresses.MECHANIC.getName(), null);

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