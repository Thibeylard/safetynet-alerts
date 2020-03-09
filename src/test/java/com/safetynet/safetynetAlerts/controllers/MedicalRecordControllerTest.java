package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.services.MedicalRecordService;
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
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordController.class)
@DisplayName("MedicalRecordController tests on :")
class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean
    private MedicalRecordService mockMedicalRecordService;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeEach
    void resetMap() {
        params.clear();
    }

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class MedicalRecordAddMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("add_Success")
        void Given_validRequest_When_add_Then_statusIsCreated() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            doReturn(true).when(mockMedicalRecordService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("birthDate"),
                    params.get("medications"),
                    params.get("allergies"));
            try {
                mvcMock.perform(post("/medicalRecord")
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
        void Given_missingParameterToUpdate_When_add_Then_statusIsBadRequest() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            // allergies parameter missing
            try {
                mvcMock.perform(post("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ forbidden
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("add_Forbidden")
        void Given_illegalDataOverrideException_When_add_Then_statusIsForbidden() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            doThrow(new IllegalDataOverrideException()).when(mockMedicalRecordService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("birthDate"),
                    params.get("medications"),
                    params.get("allergies"));
            try {
                mvcMock.perform(post("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("add_ServerError")
        void Given_IOException_When_add_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            doThrow(new IOException()).when(mockMedicalRecordService).add(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    params.getFirst("birthDate"),
                    params.get("medications"),
                    params.get("allergies"));
            try {
                mvcMock.perform(post("/medicalRecord")
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
    class MedicalRecordUpdateMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("update_Success")
        void Given_validRequest_When_update_Then_statusIsNoContent() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            // Works even with not all parameters

            MultiValueMap<String, String> optParams = new LinkedMultiValueMap<String, String>();
            optParams.add("birthDate", params.getFirst("birthDate"));

            doReturn(true).when(mockMedicalRecordService).update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    Optional.ofNullable(params.getFirst("birthDate")),
                    Optional.empty(),
                    Optional.empty());
            try {
                mvcMock.perform(put("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ bad request
        @Test
        @Tag("BadRequestStatus")
        @DisplayName("update_MissingRequired_BadRequest")
        void Given_missingRequiredParameter_When_update_Then_statusIsBadRequest() {
            params.add("firstName", "someFirstName");
            // No required parameter lastName
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            try {
                mvcMock.perform(put("/medicalRecord")
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
                mvcMock.perform(put("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ notFound
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("update_notFound")
        void Given_noSuchDataException_When_update_Then_statusIsNotFound() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");

            doThrow(new NoSuchDataException()).when(mockMedicalRecordService).update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    Optional.ofNullable(params.getFirst("birthDate")),
                    Optional.empty(),
                    Optional.empty());
            try {
                mvcMock.perform(put("/medicalRecord")
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
        @DisplayName("updateServerError")
        void Given_IOException_When_update_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");

            doThrow(new IOException()).when(mockMedicalRecordService).update(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"),
                    Optional.ofNullable(params.getFirst("birthDate")),
                    Optional.empty(),
                    Optional.empty());
            try {
                mvcMock.perform(put("/medicalRecord")
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
    class MedicalRecordDeleteMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @Tag("SuccessStatus")
        @DisplayName("deleteSuccess")
        void Given_validRequest_When_delete_Then_statusIsNoContent() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doReturn(true).when(mockMedicalRecordService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(delete("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
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
                mvcMock.perform(delete("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ notFound
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("delete_notFound")
        void Given_noSuchDataException_When_delete_Then_statusIsNotFound() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new NoSuchDataException()).when(mockMedicalRecordService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(delete("/medicalRecord")
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
        @DisplayName("deleteServerError")
        void Given_IOException_When_delete_Then_statusIsInternalServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            doThrow(new IOException()).when(mockMedicalRecordService).delete(
                    params.getFirst("firstName"),
                    params.getFirst("lastName"));
            try {
                mvcMock.perform(delete("/medicalRecord")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

}