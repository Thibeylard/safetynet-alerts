package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("MedicalRecordController integration tests :")
public class MedicalRecordControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;

    // URLS
    private final static String medicalRecordIDURL = "/medicalRecord?firstName={firstName}&lastName={lastName}";
    private final static String birthDateURLParam = "&birthDate={birthDate}";
    private final static String medicationsURLParam = "&medications={medications}";
    private final static String allergiesURLParam = "&allergies={allergies}";

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
    void Given_dataAddedToDatabase_When_POSTMedicalRecord_Then_correspondingDataCanBeRetrieved() {
        MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(
                null,
                null,
                null,
                List.of("medic:400mg", "medic2:200mg"),
                Collections.emptyList(),
                false);

        // Checking that this specific medicalRecord doesn't already exist in database.
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();

        // Actual POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + medicationsURLParam + medicationsURLParam + allergiesURLParam, null, String.class,
                medicalRecord.getFirstName(),
                medicalRecord.getLastName(),
                medicalRecord.getBirthDate(),
                medicalRecord.getMedications().get(0),
                medicalRecord.getMedications().get(1),
                "");

        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(medicalRecord);
    }


    @Test
    @DisplayName("POST no partial data")
    void Given_wrongParameters_When_POSTMedicalRecord_Then_noPartialDataSaved() {
        MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(
                null,
                null,
                null,
                Collections.emptyList(),
                List.of("peanuts", "cats"),
                false);

        // Checking that this specific medicalRecord doesn't already exist in database.
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();

        // Actual POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + allergiesURLParam + allergiesURLParam, null, String.class,
                medicalRecord.getFirstName(),
                medicalRecord.getLastName(),
                medicalRecord.getBirthDate(),
                medicalRecord.getAllergies().get(0),
                medicalRecord.getAllergies().get(1));

        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, medicalRecord.getFirstName(), medicalRecord.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingPerson_When_POSTMedicalRecord_Then_personNotReplaced() {
        // Two medicalRecords with same name but different allergies and birthDate
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                false);

        MedicalRecord added = MedicalRecordFactory.createMedicalRecord(
                existing.getFirstName(),
                existing.getLastName(),
                null,
                Collections.emptyList(),
                List.of("gluten"),
                false);


        // Preparation POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + medicationsURLParam + allergiesURLParam, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getBirthDate(),
                "",
                "");

        // Checking that existing MedicalRecord is actually in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();


        // Actual POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + medicationsURLParam + allergiesURLParam, null, String.class,
                added.getFirstName(),
                added.getLastName(),
                added.getBirthDate(),
                "",
                added.getAllergies().get(0));

        // Checking that existing person is unmodified after post
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, added.getFirstName(), added.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEMedicalRecord_Then_retrievedDataIsAccordinglyUpdated() {
        // Two medicalRecords with same name but different medications
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(
                null,
                null,
                null,
                List.of("test:250mg"),
                Collections.emptyList(),
                false);

        MedicalRecord updated = MedicalRecordFactory.createMedicalRecord(
                existing.getFirstName(),
                existing.getLastName(),
                existing.getBirthDate(),
                Collections.emptyList(),
                existing.getAllergies(),
                false);


        // Preparation POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + medicationsURLParam + allergiesURLParam, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getBirthDate(),
                existing.getMedications().get(0),
                "");

        // Checking that existing MedicalRecord is actually in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        // MedicalRecord PUT request on 'medications' only
        restTemplate.put(medicalRecordIDURL + medicationsURLParam, null,
                updated.getFirstName(),
                updated.getLastName(),
                "");

        // Checking that existing person is correctly modified after put
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(updated);
    }

    @Test
    @DisplayName("UPDATE no partial data")
    void Given_noCorrespondingData_When_UPDATEMedicalRecord_Then_noPartialDataSaved() {
        MedicalRecord updated = MedicalRecordFactory.createMedicalRecord(false);

        // Checking that MedicalRecord is not in database
        ResponseEntity<MedicalRecord> response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNull();

        // Actual request
        restTemplate.put(medicalRecordIDURL + birthDateURLParam, null,
                updated.getFirstName(),
                updated.getLastName(),
                "14/02/1992");

        // Checking that existing person is not added after put
        response = restTemplate.getForEntity(medicalRecordIDURL, MedicalRecord.class, updated.getFirstName(), updated.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEMedicalRecord_Then_dataCantBeRetrievedAfterDeletion() {
        MedicalRecord existing = MedicalRecordFactory.createMedicalRecord(
                null,
                null,
                null,
                Collections.emptyList(),
                Collections.emptyList(),
                false);

        // Preparation POST request
        restTemplate.postForEntity(medicalRecordIDURL + birthDateURLParam + medicationsURLParam + allergiesURLParam, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getBirthDate(),
                "",
                "");

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