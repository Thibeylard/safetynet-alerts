package com.safetynet.safetynetAlerts.integration;

import com.safetynet.safetynetAlerts.daos.JsonFileDatabase;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("PersonController integration tests :")
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    private final static String getURL = "/person?firstName={firstName}&lastName={lastName}";
    @Autowired
    private JsonFileDatabase jsonFileDatabase;


    @BeforeEach
    void resetParams() {
        params.clear();
    }

    @Test
    @DisplayName("POST successful")
    void Given_dataAddedToDatabase_When_POSTPerson_Then_correspondingDataCanBeRetrieved() throws Exception {
        Person person = PersonFactory.getPerson();

        // Checking that this specific person doesn't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, person.getFirstName(), person.getLastName());
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

        response = restTemplate.getForEntity(getURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(person);
    }


    @Test
    @DisplayName("POST missing parameter")
    void Given_wrongParameters_When_POSTPerson_Then_noPartialDataSaved() throws Exception {
        Person person = PersonFactory.getPerson();

        // Checking that this specific person doesn't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, person.getFirstName(), person.getLastName());
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

        response = restTemplate.getForEntity(getURL, Person.class, person.getFirstName(), person.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(person);
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingPerson_When_POSTPerson_Then_personNotReplaced() throws Exception {
        // Two persons with same name but different addresses
        Person existing = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.APPLEGATE), Optional.empty());
        Person added = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());

        // Checking that these specific persons don't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNull();
        response = restTemplate.getForEntity(getURL, Person.class, added.getFirstName(), added.getLastName());
        assertThat(response.getBody())
                .isNull();

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("address", existing.getAddress());
        params.add("city", existing.getCity());
        params.add("zip", existing.getZip());
        params.add("phone", existing.getPhone());
        params.add("email", existing.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

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

        // Checking that existing person is unmodified
        response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    //TODO replace jsonFileDatabase.getPerson() by get requests
    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEPerson_Then_retrievedDataIsAccordinglyUpdated() throws Exception {
        // Two persons with same name but different addresses
        Person original = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.APPLEGATE), Optional.empty());
        Person update = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());

        // Checking that this specific persons don't already exist in database.
        assertThat(jsonFileDatabase.getPerson(original.getFirstName(), original.getLastName()))
                .isNull();
        assertThat(jsonFileDatabase.getPerson(update.getFirstName(), update.getLastName()))
                .isNull();

        params.add("firstName", original.getFirstName());
        params.add("lastName", original.getLastName());
        params.add("address", original.getAddress());
        params.add("city", original.getCity());
        params.add("zip", original.getZip());
        params.add("phone", original.getPhone());
        params.add("email", original.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

        // Checking that this specific person doesn't already exist in database.
        assertThat(jsonFileDatabase.getPerson(original.getFirstName(), original.getLastName()))
                .isNotNull();

        params.clear();

        params.add("firstName", update.getFirstName());
        params.add("lastName", update.getLastName());
        params.add("address", update.getAddress());
        params.add("city", update.getCity());
        params.add("zip", update.getZip());
        params.add("phone", update.getPhone());
        params.add("email", update.getEmail());

        // Actual request
        restTemplate.put("/person", params);

        // Checking that existing person is unmodified
        assertThat(jsonFileDatabase.getPerson(original.getFirstName(), original.getLastName()))
                .isNotNull()
                .isEqualToComparingFieldByField(update);
    }

    @Test
    @DisplayName("UPDATE no data")
    void Given_noCorrespondingData_When_UPDATEPerson_Then_dataNotCreated() throws Exception {
        // Two persons with same name but different addresses
        Person update = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());

        // Checking that this specific persons don't already exist in database.
        assertThat(jsonFileDatabase.getPerson(update.getFirstName(), update.getLastName()))
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

        // Checking that existing person is unmodified
        assertThat(jsonFileDatabase.getPerson(update.getFirstName(), update.getLastName()))
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEPerson_Then_dataCanBeRetrievedAfterDeletion() throws Exception {
        Person person = PersonFactory.getPerson();

        params.add("firstName", person.getFirstName());
        params.add("lastName", person.getLastName());
        params.add("address", person.getAddress());
        params.add("city", person.getCity());
        params.add("zip", person.getZip());
        params.add("phone", person.getPhone());
        params.add("email", person.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

        assertThat(jsonFileDatabase.getPerson(person.getFirstName(), person.getLastName()))
                .isEqualToComparingFieldByField(person);
    }
}