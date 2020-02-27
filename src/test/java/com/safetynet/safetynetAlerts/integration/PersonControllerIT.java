package com.safetynet.safetynetAlerts.integration;

import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("PersonController integration tests :")
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    private final static String getURL = "/person?firstName={firstName}&lastName={lastName}";

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
    @DisplayName("POST no partial data")
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
                .isNull();
    }

    @Test
    @DisplayName("POST already exists")
    void Given_existingPerson_When_POSTPerson_Then_personNotReplaced() throws Exception {
        // Two persons with same name but different addresses
        Person existing = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.APPLEGATE), Optional.empty());
        Person added = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());


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
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
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
        response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(existing);
    }

    @Test
    @DisplayName("UPDATE successful")
    void Given_dataAddedToDatabase_When_UPDATEPerson_Then_retrievedDataIsAccordinglyUpdated() throws Exception {
        // Two persons with same name but different addresses
        Person existing = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.APPLEGATE), Optional.empty());
        Person update = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());

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
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull()
                .isEqualToComparingFieldByField(update);
    }

    @Test
    @DisplayName("UPDATE no partial data")
    void Given_noCorrespondingData_When_UPDATEPerson_Then_noPartialDataSaved() throws Exception {
        // Two persons with same name but different addresses
        Person update = PersonFactory.getPerson(Optional.of("Bob"), Optional.of("Hawkins"), Optional.of(Addresses.BAYMEADOWS), Optional.empty());

        // Checking that this specific persons don't already exist in database.
        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, update.getFirstName(), update.getLastName());
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
        response = restTemplate.getForEntity(getURL, Person.class, update.getFirstName(), update.getLastName());
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("DELETE successful")
    void Given_dataAddedToDatabase_When_DELETEPerson_Then_dataCantBeRetrievedAfterDeletion() {
        Person existing = PersonFactory.getPerson();

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());
        params.add("address", existing.getAddress());
        params.add("city", existing.getCity());
        params.add("zip", existing.getZip());
        params.add("phone", existing.getPhone());
        params.add("email", existing.getEmail());

        // Preparation request
        restTemplate.postForEntity("/person", params, String.class);

        ResponseEntity<Person> response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNotNull();

        params.clear();

        params.add("firstName", existing.getFirstName());
        params.add("lastName", existing.getLastName());

        // Actual request
        restTemplate.delete("/person?firstName={firstName}&lastName={lastName}", existing.getFirstName(), existing.getLastName());

        // Check that existing Person has been deleted
        response = restTemplate.getForEntity(getURL, Person.class, existing.getFirstName(), existing.getLastName());
        assertThat(response.getBody())
                .isNull();
    }
}