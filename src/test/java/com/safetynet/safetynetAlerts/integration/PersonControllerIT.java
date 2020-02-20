package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("PersonController integration tests :")
public class PersonControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    private Person personData = PersonFactory.getPerson(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());

    private static ObjectMapper mapper;
    private final static File TEST_DATA = new File("src/test/resources/testData.json");

    private static JsonFileDatabaseDTO fileOriginalContent;

    private List<Firestation> firestations = new ArrayList<Firestation>();
    private List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
    private List<Person> persons = new ArrayList<Person>();


    @BeforeEach
    void resetParams() {
        params.clear();
    }

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class PersonAddMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("AddSuccess")
        @DisplayName("add_SuccessStatus")
        void Given_validRequest_When_add_Then_statusIsCreatedAndDataSaved() throws IOException {
            params.add("firstName", personData.getFirstName());
            params.add("lastName", personData.getLastName());
            params.add("address", personData.getAddress());
            params.add("city", personData.getCity());
            params.add("zip", personData.getZip());
            params.add("phone", personData.getPhone());
            params.add("email", personData.getEmail());

            ResponseEntity<String> responseEntity = restTemplate
                    .postForEntity("/person",params, String.class);

            assertThat(responseEntity.getStatusCode())
                    .isEqualByComparingTo(HttpStatus.CREATED);

            persons = mapper.convertValue(mapper.readTree(TEST_DATA).at("/persons"), new TypeReference<List<Person>>() {
            });

            assertThat(persons)
                    .isNotNull()
                    .isNotEmpty()
                    .usingRecursiveFieldByFieldElementComparator()
                    .contains(personData);

        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("add_MissingParameter_BadRequest")
        void Given_missingParameter_When_add_Then_statusIsBadRequestAndDataNotSaved() {
            params.add("firstName", personData.getFirstName());
            params.add("lastName", personData.getLastName());
            params.add("address", personData.getAddress());
            params.add("city", personData.getCity());
            params.add("zip", personData.getZip());
            params.add("phone", personData.getPhone());
            // email parameter missing


        }

        //    ------------------------------------------------------------------------------ server error
    /*@Test
    @Tag("ServerErrorStatus")
    @DisplayName("addServerError")
    void Given_validRequestButServiceNotWorking_When_add_Then_statusIsInternalServerError() {
        params.add("firstName", personData.getFirstName());
        params.add("lastName", personData.getLastName());
        params.add("address", personData.getAddress());
        params.add("city", personData.getCity());
        params.add("zip", personData.getZip());
        params.add("phone", personData.getPhone());
        params.add("email", personData.getEmail());

        try {
            mvcMock.perform(post("/person")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }*/
    }

    //    ------------------------------------------------------------------------------ UPDATE
//    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("update()")
    class PersonUpdateMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("updateSuccess")
        void Given_validRequest_When_update_Then_statusIsNoContent() {
            params.add("firstName", personData.getFirstName());
            params.add("lastName", personData.getLastName());
            params.add("phone", "differentPhoneNumber");
            // Works even with not all parameters

        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("updateMissingRequiredBadRequest")
        void Given_missingRequiredParameter_When_update_Then_statusIsBadRequest() {
            params.add("firstName", personData.getFirstName());
            // No required parameter lastName
            params.add("address", personData.getAddress());
            params.add("city", personData.getCity());
            params.add("zip", personData.getZip());

        }

        @Test
        @Tag("BadRequestStatus")
        @DisplayName("update_NoParameterToUpdate_BadRequest")
        void Given_missingParameterToUpdate_When_update_Then_statusIsBadRequest() {
            params.add("firstName", personData.getFirstName());
            params.add("lastName", personData.getLastName());
            // Only required and immutable parameters were given.

        }

        //    ------------------------------------------------------------------------------ server error
/*    @Test
    @Tag("ServerErrorStatus")
    @DisplayName("update_ServerError")
    void Given_validRequestButServiceNotWorking_When_update_Then_statusIsInternalServerError() {
        params.add("firstName", "someFirstName");
        params.add("lastName", "someLastName");
        params.add("phone", "somePhone");

        MultiValueMap<String, String> optParams = new LinkedMultiValueMap<String, String>();
        optParams.add("phone", params.getFirst("phone"));

        try {
            mvcMock.perform(put("/person")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }*/
    }

    //    ------------------------------------------------------------------------------ DELETE
//    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class PersonDeleteMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("delete_Success")
        void Given_validRequest_When_delete_Then_statusIsNoContent() {
            params.add("firstName", personData.getFirstName());
            params.add("lastName", personData.getLastName());

        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("delete_MissingParameter_BadRequest")
        void Given_missingParameter_When_delete_Then_statusIsBadRequest() {
            // Required parameter firstName is missing
            params.add("lastName", personData.getLastName());

        }

        //    ------------------------------------------------------------------------------ server error
/*    @Test
    @Tag("ServerErrorStatus")
    @DisplayName("delete_ServerError")
    void Given_validRequestButServiceNotWorking_When_delete_Then_statusIsInternalServerError() {
        params.add("firstName", personData.getFirstName());
        params.add("lastName", personData.getLastName());

        try {
            mvcMock.perform(delete("/person")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }*/
    }

}