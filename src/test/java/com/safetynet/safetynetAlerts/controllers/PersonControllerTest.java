package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.models.Person;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        @DisplayName("add_Success")
        void Given_validRequest_When_add_Then_statusIsCreated() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            params.add("email", "someMail");
            doReturn(true).when(mockPersonService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("address"),
                    params.getFirst("city"),
                    params.getFirst("zip"),
                    params.getFirst("phone"),
                    params.getFirst("email"));
            try {
                mvcMock.perform(post("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ forbidden
        @Test
        @Tag("ErrorStatus")
        @DisplayName("add_IllegalDataOverride")
        void Given_illegalDataOverrideException_When_add_Then_statusIsForbidden() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            params.add("email", "someMail");
            doThrow(new IllegalDataOverrideException()).when(mockPersonService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("address"),
                    params.getFirst("city"),
                    params.getFirst("zip"),
                    params.getFirst("phone"),
                    params.getFirst("email"));
            try {
                mvcMock.perform(post("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ErrorStatus")
        @DisplayName("add_IOException")
        void Given_IOException_When_add_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("address", "someAddress");
            params.add("city", "someCity");
            params.add("zip", "someZip");
            params.add("phone", "somePhone");
            params.add("email", "someMail");
            doThrow(new IOException()).when(mockPersonService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("address"),
                    params.getFirst("city"),
                    params.getFirst("zip"),
                    params.getFirst("phone"),
                    params.getFirst("email"));
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
        @DisplayName("update_Success")
        void Given_validRequest_When_update_Then_statusIsOK() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("phone", "somePhone");
            // Works even with not all parameters

            doReturn(true).when(mockPersonService)
                    .update(
                            params.getFirst("firstName"),
                            params.getFirst("lastName"),
                            null,
                            null,
                            null,
                            params.getFirst("phone"),
                            null);
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ not found
        @Test
        @Tag("ErrorStatus")
        @DisplayName("update_NoSuchDataException")
        void Given_noSuchDataException_When_update_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("phone", "somePhone");

            MultiValueMap<String, String> optParams = new LinkedMultiValueMap<String, String>();
            optParams.add("phone", params.getFirst("phone"));

            doThrow(new NoSuchDataException()).when(mockPersonService).update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    null,
                    null,
                    null,
                    params.getFirst("phone"),
                    null);
            try {
                mvcMock.perform(put("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("update_IOException")
        void Given_validRequestButServiceNotWorking_When_update_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("phone", "somePhone");

            MultiValueMap<String, String> optParams = new LinkedMultiValueMap<String, String>();
            optParams.add("phone", params.getFirst("phone"));

            doThrow(new IOException()).when(mockPersonService).update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    null,
                    null,
                    null,
                    params.getFirst("phone"),
                    null);
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
        void Given_validRequest_When_delete_Then_statusIsOK() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doReturn(true).when(mockPersonService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(delete("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ badRequest
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("update_NoParameterToUpdate")
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
        @Tag("ErrorStatus")
        @DisplayName("delete_NoSuchDataException")
        void Given_noSuchDataException_When_delete_Then_statusIsNotFound() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new NoSuchDataException()).when(mockPersonService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(delete("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("delete_ServerError")
        void Given_IOException_When_delete_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new IOException()).when(mockPersonService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
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

    //    ------------------------------------------------------------------------------ DELETE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class PersonGetMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("get_Success")
        void Given_validRequest_When_get_Then_statusIsOK() throws Exception {
            Person person = PersonFactory.createPerson();
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");

            doReturn(person).when(mockPersonService).get(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(get("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ErrorStatus")
        @DisplayName("get_NoSuchDataException")
        void Given_noSuchDataException_When_get_Then_statusIsNotFound() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new NoSuchDataException()).when(mockPersonService).get(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(get("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("get_ServerError")
        void Given_IOException_When_get_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new IOException()).when(mockPersonService).get(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(get("/person")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }
}