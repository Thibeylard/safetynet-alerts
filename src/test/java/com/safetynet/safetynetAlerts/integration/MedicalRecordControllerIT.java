package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.assertj.core.util.Lists;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("MedicalRecordController integration tests :")
public class MedicalRecordControllerIT {


    private final static String medicalRecordIDURL = "/medicalRecord?firstName={firstName}&lastName={lastName}";
    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;
    @Autowired
    private TestRestTemplate restTemplate;
    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeAll
    public static void saveData(@Value("${jsondatabase.src}") String src) throws IOException {
        data = new File(src);
        factory = new JsonFactory().setCodec(new ObjectMapper());
        JsonParser parser = factory.createParser(MedicalRecordControllerIT.data);
        jsonFileDTO = parser.readValueAs(JsonFileDatabaseDTO.class);
    }


    @AfterAll
    public static void resetData() {
        try {
            JsonGenerator generator = factory.createGenerator(new File("src/test/resources/data.json"), JsonEncoding.UTF8);
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
    void Given_dataAddedToDatabase_When_POSTMedicalRecord_Then_correspondingDataCanBeRetrieved() throws Exception {
        MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(false);

        // Checking that this specific medicalRecord doesn't already exist in database.
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", medicalRecord.getFirstName());
        params.add("lastName", medicalRecord.getLastName());
        params.add("birthDate", medicalRecord.getBirthDate());
        params.addAll("medications", medicalRecord.getMedications());
        params.addAll("allergies", medicalRecord.getAllergies());

        // Actual request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(medicalRecord);
    }


    @Test
    @DisplayName("POST no partial data")
    void Given_wrongParameters_When_POSTMedicalRecord_Then_noPartialDataSaved() throws Exception {
        MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(false);

        // Checking that this specific medicalRecord doesn't already exist in database.
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", medicalRecord.getFirstName());
        params.add("lastName", medicalRecord.getLastName());
        params.add("birthDate", medicalRecord.getBirthDate());
        // medications is missing
        params.addAll("allergies", medicalRecord.getAllergies());

        // Actual request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingPerson_When_POSTMedicalRecord_Then_personNotReplaced() throws Exception {
        // Two medicalRecords with same name but different values
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(false);
        MedicalRecord added = MedicalRecordFactory.createMedicalRecord(Optional.of(existing.getFirstName()), Optional.ofNullable(existing.getLastName()), false);


        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("birthDate", existing.getBirthDate());
        params.addAll("medications", existing.getMedications());
        params.addAll("allergies", existing.getAllergies());

        // Preparation request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        // Checking that existing MedicalRecord is actually in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        params.add("firstName", added.getFirstName());
        params.add("lastName", added.getLastName());

        // Actual request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        // Checking that existing person is unmodified after post
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, added.getFirstName(), added.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEMedicalRecord_Then_retrievedDataIsAccordinglyUpdated() throws Exception {
        // Two medicalRecords with same name but different values
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(false);
        MedicalRecord updated = MedicalRecordFactory.createMedicalRecord(
                Optional.of(existing.getFirstName()),
                Optional.of(existing.getLastName()),
                Optional.of(existing.getBirthDate()),
                Optional.of(List.of("test:250mg")),
                Optional.of(Lists.emptyList()),
                false);
        //TODO Optionals in parameters with list break method : Empty List is considered empty parameters, which is wrong.

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("birthDate", existing.getBirthDate());
        params.addAll("medications", existing.getMedications());
        params.addAll("allergies", existing.getAllergies());

        // Preparation request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        // Checking that existing MedicalRecord is actually in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        params.add("firstName", updated.getFirstName());
        params.add("lastName", updated.getLastName());
        params.addAll("medications", updated.getMedications());
        params.addAll("allergies", updated.getAllergies());

        // Actual request
        restTemplate.put("/medicalRecord", params);

        // Checking that existing person is correctly modified after put
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(updated);
    }

    @Test
    @DisplayName("UPDATE no partial data")
    void Given_noCorrespondingData_When_UPDATEMedicalRecord_Then_noPartialDataSaved() throws Exception {
        MedicalRecord updated = MedicalRecordFactory.createMedicalRecord(false);

        // Checking that MedicalRecord is not in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", updated.getFirstName());
        params.add("lastName", updated.getLastName());
        params.addAll("medications", updated.getMedications());
        params.addAll("allergies", updated.getAllergies());

        // Actual request
        restTemplate.put("/medicalRecord", params);

        // Checking that existing person is not added after put
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEMedicalRecord_Then_dataCantBeRetrievedAfterDeletion() {
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(false);

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("birthDate", existing.getBirthDate());
        params.addAll("medications", existing.getMedications());
        params.addAll("allergies", existing.getAllergies());

        // Preparation request
        restTemplate.postForEntity("/medicalRecord", params, String.class);

        // Checking that existing MedicalRecord is actually in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        // Actual request
        restTemplate.delete(medicalRecordIDURL, existing.getFirstName(), existing.getLastName());

        // Checking that existing person is correctly deleted
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNull();
    }
}