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
    private final static String addressURL = "&address={address}";
    private final static String cityURL = "&city={city}";
    private final static String zipURL = "&zip={zip}";
    private final static String phoneURL = "&phone={phone}";
    private final static String emailURL = "&email={email}";

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

        // Actual request
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null, String.class,
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getCity(),
                person.getZip(),
                person.getPhone(),
                person.getEmail());

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

        // Actual request
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + emailURL, null, String.class,
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getCity(),
                person.getZip(),
                person.getEmail());

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

        // Preparation request
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getAddress(),
                existing.getCity(),
                existing.getZip(),
                existing.getPhone(),
                existing.getEmail());

        // Checking that existing Person is actually in database
        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        // Actual request
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null, String.class,
                added.getFirstName(),
                added.getLastName(),
                added.getAddress(),
                added.getCity(),
                added.getZip(),
                added.getPhone(),
                added.getEmail());

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
        update.setPhone(existing.getPhone());

        // Person added
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getAddress(),
                existing.getCity(),
                existing.getZip(),
                existing.getPhone(),
                existing.getEmail());

        // Person updated
        restTemplate.put(personIDURL + addressURL + cityURL + zipURL, null,
                update.getFirstName(),
                update.getLastName(),
                update.getAddress(),
                update.getCity(),
                update.getZip());

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

        // Actual request
        restTemplate.put(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null,
                update.getFirstName(),
                update.getLastName(),
                update.getAddress(),
                update.getCity(),
                update.getZip(),
                update.getPhone(),
                update.getEmail());

        // Checking that no data has been created
        response = restTemplate.getForEntity(personIDURL, Person.class, update.getFirstName(), update.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEPerson_Then_dataCantBeRetrievedAfterDeletion() {
        Person existing = PersonFactory.createPerson();

        // Preparation request
        restTemplate.postForEntity(personIDURL + addressURL + cityURL + zipURL + phoneURL + emailURL, null, String.class,
                existing.getFirstName(),
                existing.getLastName(),
                existing.getAddress(),
                existing.getCity(),
                existing.getZip(),
                existing.getPhone(),
                existing.getEmail());


        ResponseEntity<Person> response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        // Actual request
        restTemplate.delete(personIDURL, existing.getFirstName(), existing.getLastName());

        // Check that existing Person has been deleted
        response = restTemplate.getForEntity(personIDURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNull();
    }
}