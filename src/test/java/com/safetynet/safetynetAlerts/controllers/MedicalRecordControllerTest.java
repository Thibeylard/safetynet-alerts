package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
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
        void Given_validRequest_When_add_Then_statusIsCreated() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            when(mockMedicalRecordService.add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList()))
                    .thenReturn(true);
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

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("add_ServerError")
        void Given_validRequestButServiceNotWorking_When_add_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            params.addAll("medications", Collections.singletonList("someMedic:somePosology"));
            params.addAll("allergies", Collections.singletonList("someAllergy"));
            when(mockMedicalRecordService.add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList()))
                    .thenReturn(false);
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
        void Given_validRequest_When_update_Then_statusIsNoContent() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            // Works even with not all parameters
            when(mockMedicalRecordService.update(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList()))
                    .thenReturn(true);
            try {
                mvcMock.perform(put("/medicalRecord")
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

        //    ------------------------------------------------------------------------------ server error
        @Test
        @Tag("ServerErrorStatus")
        @DisplayName("updateServerError")
        void Given_validRequestButServiceNotWorking_When_update_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            params.add("birthDate", "someBirthDate");
            when(mockMedicalRecordService.update(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList()))
                    .thenReturn(false);
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
        void Given_validRequest_When_delete_Then_statusIsNoContent() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockMedicalRecordService.delete(
                    anyString(),
                    anyString()))
                    .thenReturn(true);
            try {
                mvcMock.perform(delete("/medicalRecord")
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
                mvcMock.perform(delete("/medicalRecord")
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
        @DisplayName("deleteServerError")
        void Given_validRequestButServiceNotWorking_When_delete_Then_statusIsInternalServerError() {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockMedicalRecordService.delete(
                    anyString(),
                    anyString()))
                    .thenReturn(false);
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