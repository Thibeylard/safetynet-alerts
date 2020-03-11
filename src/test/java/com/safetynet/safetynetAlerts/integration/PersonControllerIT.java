package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Person;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("PersonController integration tests :")
public class PersonControllerIT {


    @Autowired
    private TestRestTemplate restTemplate;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    private final static String personIDURL = "/person?firstName={firstName}&lastName={lastName}";

    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;

    @BeforeAll
    public static void saveData(@Value("${jsondatabase.src}") String src) throws IOException {
        data = new File(src);
        factory = new JsonFactory().setCodec(new ObjectMapper());
        JsonParser parser = factory.createParser(PersonControllerIT.data);
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
    void Given_dataAddedToDatabase_When_POSTPerson_Then_correspondingDataCanBeRetrieved() throws Exception {
        Person person = PersonFactory.createPerson();

        // Checking that this specific person doesn't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", person.getFirstName());
        params.add("lastName", person.getLastName());
        params.add("address", person.getAddress());
        params.add("city", person.getCity());
        params.add("zip", person.getZip());
        params.add("phone", person.getPhone());
        params.add("email", person.getEmail());

        // Actual request
        restTemplate.postForEntity("/person", params, String.class);

        response = restTemplate.getForEntity(personIDURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(person);
    }


    @Test
    @DisplayName("POST no partial data")
    void Given_wrongParameters_When_POSTPerson_Then_noPartialDataSaved() throws Exception {
        Person person = PersonFactory.createPerson();

        // Checking that this specific person doesn't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", person.getFirstName());
        params.add("lastName", person.getLastName());
        params.add("address", person.getAddress());
        params.add("city", person.getCity());
        params.add("zip", person.getZip());
        // No phone number provided
        params.add("email", person.getEmail());

        // Actual request
        restTemplate.postForEntity("/person", params, String.class);

        response = restTemplate.getForEntity(personIDURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingPerson_When_POSTPerson_Then_personNotReplaced() throws Exception {
        // Two persons with same name but different addresses
        Person existing = PersonFactory.createPerson();
        Person added = PersonFactory.createPerson(existing.getFirstName(), existing.getLastName(), null, null);


        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("address", existing.getAddress());
        params.add("city", existing.getCity());
        params.add("zip", existing.getZip());
        params.add("phone", existing.getPhone());
        params.add("email", existing.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

        // Checking that existing Person is actually in database
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        params.add("firstName", added.getFirstName());
        params.add("lastName", added.getLastName());
        params.add("address", added.getAddress());
        params.add("city", added.getCity());
        params.add("zip", added.getZip());
        params.add("phone", added.getPhone());
        params.add("email", added.getEmail());

        // Actual request
        restTemplate.postForEntity("/person", params, String.class);

        // Checking that existing person is unmodified after post
        response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEPerson_Then_retrievedDataIsAccordinglyUpdated() throws Exception {
        // Two persons with same name but different addresses
        Person existing = PersonFactory.createPerson(null, null, Addresses.APPLEGATE, null);
        Person update = PersonFactory.createPerson(existing.getFirstName(), existing.getLastName(), Addresses.BAYMEADOWS, null);

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("address", existing.getAddress());
        params.add("city", existing.getCity());
        params.add("zip", existing.getZip());
        params.add("phone", existing.getPhone());
        params.add("email", existing.getEmail());

        // Person added
        restTemplate.postForEntity("/person", params, String.class);

        params.clear();

        params.add("firstName", update.getFirstName());
        params.add("lastName", update.getLastName());
        params.add("address", update.getAddress());
        params.add("city", update.getCity());
        params.add("zip", update.getZip());
        params.add("phone", update.getPhone());
        params.add("email", update.getEmail());

        // Person updated
        restTemplate.put("/person", params);

        // Checking that existing person has been modified
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(update);
    }

    @Test
    @DisplayName("UPDATE no partial data")
    void Given_noCorrespondingData_When_UPDATEPerson_Then_noPartialDataSaved() throws Exception {
        Person update = PersonFactory.createPerson();

        // Checking that this specific persons don't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, update.getFirstName(), update.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", update.getFirstName());
        params.add("lastName", update.getLastName());
        params.add("address", update.getAddress());
        params.add("city", update.getCity());
        params.add("zip", update.getZip());
        params.add("phone", update.getPhone());
        params.add("email", update.getEmail());

        // Actual request
        restTemplate.put("/person", params);

        // Checking that no data has been created
        response = restTemplate.getForEntity(personIDURL, Person.class, update.getFirstName(), update.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEPerson_Then_dataCantBeRetrievedAfterDeletion() {
        Person existing = PersonFactory.createPerson();

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("address", existing.getAddress());
        params.add("city", existing.getCity());
        params.add("zip", existing.getZip());
        params.add("phone", existing.getPhone());
        params.add("email", existing.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());

        // Actual request
        restTemplate.delete(personIDURL, existing.getFirstName(), existing.getLastName());

        // Check that existing Person has been deleted
        response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNull();
    }
}