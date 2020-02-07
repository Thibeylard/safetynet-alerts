package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.PersonService;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
@DisplayName("PersonController tests on :")
class PersonControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean
    private PersonService mockPersonService;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeEach
    void resetMap() {
        params.clear();
    }

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class PersonAddMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("addSuccess")
        void Given_validRequest_When_add_Then_statusIsCreated() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            params.add("email", "someMail");
            when(mockPersonService.add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("address"),
                    params.getFirst("city"),
                    params.getFirst("zip"),
                    params.getFirst("phone"),
                    params.getFirst("email")))
                    .thenReturn(true);
            try {
                mvcMock.perform(post("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("add_MissingParameter_BadRequest")
        void Given_missingParameter_When_add_Then_statusIsBadRequest() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            // email parameter missing
            try {
                mvcMock.perform(post("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("addServerError")
        void Given_validRequestButServiceNotWorking_When_add_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            params.add("email", "someMail");
            when(mockPersonService.add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("address"),
                    params.getFirst("city"),
                    params.getFirst("zip"),
                    params.getFirst("phone"),
                    params.getFirst("email")))
                    .thenReturn(false);
            try {
                mvcMock.perform(post("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
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
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("phone", "somePhone");
            // Works even with not all parameters

            when(mockPersonService.update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable(params.getFirst("phone")),
                    Optional.empty()))
                    .thenReturn(true);
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("updateMissingRequiredBadRequest")
        void Given_missingRequiredParameter_When_update_Then_statusIsBadRequest() {
            params.add("firstName", "someFirstName");
            // No required parameter lastName
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @Tag("BadRequestStatus")
        @DisplayName("update_NoParameterToUpdate_BadRequest")
        void Given_missingParameterToUpdate_When_update_Then_statusIsBadRequest() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            // Only required and immutable parameters were given.
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("update_ServerError")
        void Given_validRequestButServiceNotWorking_When_update_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("phone", "somePhone");

            MultiValueMap<String, String> optParams = new LinkedMultiValueMap<String, String>();
            optParams.add("phone", params.getFirst("phone"));

            when(mockPersonService.update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable(params.getFirst("phone")),
                    Optional.empty()))
                    .thenReturn(false);
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
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
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockPersonService.delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName")))
                    .thenReturn(true);
            try {
                mvcMock.perform(delete("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("delete_MissingParameter_BadRequest")
        void Given_missingParameter_When_delete_Then_statusIsBadRequest() {
            // Required parameter firstName is missing
            params.add("lastName", "someLastName");
            try {
                mvcMock.perform(delete("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("delete_ServerError")
        void Given_validRequestButServiceNotWorking_When_delete_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockPersonService.delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName")))
                    .thenReturn(false);
            try {
                mvcMock.perform(delete("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

}