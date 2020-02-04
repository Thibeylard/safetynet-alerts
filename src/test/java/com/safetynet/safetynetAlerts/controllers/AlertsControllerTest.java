package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.services.AlertsService;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertsController.class)
@DisplayName("AlertsController Tests on :")
class AlertsControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean
    private AlertsService mockAlertsService;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeEach
    void resetMap() {
        params.clear();
    }

    //    ------------------------------------------------------------------------------ URLFirestationDTO

    @Nested
    @DisplayName("getURLFirestationDTO()")
    class AlertsControllerGetURLFirestationDTOTests {
        @Test
        void Given_validRequest_When_getURLFirestationDTO_Then_statusIsOK() throws Exception {
            params.add("stationNumber", "2");
            when(mockAlertsService.getURLFirestationDTO(
                    Integer.parseInt(Objects.requireNonNull(params.getFirst("stationNumber")))))
                    .thenReturn(any(URLFirestationDTO.class));
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLFirestationDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("stationNumber", "2");
            when(mockAlertsService.getURLFirestationDTO(
                    Integer.parseInt(Objects.requireNonNull(params.getFirst("stationNumber")))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() throws Exception {
            params.add("statioNumber", "2"); // mistyped parameter
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_mismatchParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() throws Exception {
            params.add("stationNumber", "someString"); // String instead of int
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLFirestationDTO_Then_statusIsServerError() throws Exception {
            params.add("stationNumber", "2"); // mistyped parameter
            when(mockAlertsService.getURLFirestationDTO(
                        Integer.parseInt(
                                Objects.requireNonNull(
                                        params.getFirst("stationNumber")))))
                .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLChildAlertDTO

    @Nested
    @DisplayName("getURLChildAlertDTO()")
    class AlertsControllerGetURLChildAlertDTOTests {
        @Test
        void Given_validRequest_When_getURLChildAlertDTO_Then_statusIsOK() throws Exception {
            params.add("address", "someAddress");
            when(mockAlertsService.getURLChildAlertDTO(
                    Objects.requireNonNull(params.getFirst("address"))))
                    .thenReturn(any(URLChildAlertDTO.class));
            try {
                mvcMock.perform(get("/childAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLChildAlertDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("address", "someAddress");
            when(mockAlertsService.getURLChildAlertDTO(
                    Objects.requireNonNull(params.getFirst("address"))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/childAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLChildAlertDTO_Then_statusIsBadRequest() {
            params.add("addres", "someAddress"); // mistyped parameter
            try {
                mvcMock.perform(get("/childAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLChildAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress"); // mistyped parameter
            when(mockAlertsService.getURLChildAlertDTO(
                            Objects.requireNonNull(
                                    params.getFirst("address"))))
            .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/childAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLPhoneAlertDTO
    @Nested
    @DisplayName("getURLPhoneAlertDTO()")
    class AlertsControllerGetURLPhoneAlertDTOTests {
        @Test
        void Given_validRequest_When_getURLPhoneAlertDTO_Then_statusIsOK() throws Exception {
            params.add("stationNumber", "2");
            when(mockAlertsService.getURLPhoneAlertDTO(
                    Integer.parseInt(Objects.requireNonNull(params.getFirst("stationNumber")))))
                    .thenReturn(any(URLPhoneAlertDTO.class));
            try {
                mvcMock.perform(get("/phoneAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLPhoneAlertDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("stationNumber", "2");
            when(mockAlertsService.getURLPhoneAlertDTO(
                    Integer.parseInt(Objects.requireNonNull(params.getFirst("stationNumber")))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/phoneAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLPhoneAlertDTO_Then_statusIsBadRequest() throws Exception {
            params.add("statioNumber", "2"); // mistyped parameter
            try {
                mvcMock.perform(get("/phoneAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_mismatchParameter_When_getURLPhoneAlertDTO_Then_statusIsBadRequest() {
            params.add("stationNumber", "someString"); // String instead of int
            try {
                mvcMock.perform(get("/phoneAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLPhoneAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("stationNumber", "2"); // mistyped parameter
            when(mockAlertsService.getURLPhoneAlertDTO(
                    Integer.parseInt(
                            Objects.requireNonNull(
                                    params.getFirst("stationNumber")))))
            .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/phoneAlert")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLFireDTO
    @Nested
    @DisplayName("getURLFireDTO()")
    class AlertsControllerGetURLFireDTOTests {
        @Test
        void Given_validRequest_When_getURLFireDTO_Then_statusIsOK() throws Exception {
            params.add("address", "someAddress");
            when(mockAlertsService.getURLFireDTO(
                    Objects.requireNonNull(params.getFirst("address"))))
                    .thenReturn(any(URLFireDTO.class));
            try {
                mvcMock.perform(get("/fire")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLFireDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("address", "someAddress");
            when(mockAlertsService.getURLFireDTO(
                    Objects.requireNonNull(params.getFirst("address"))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/fire")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLFireDTO_Then_statusIsBadRequest() {
            params.add("addres", "someAddress"); // mistyped parameter
            try {
                mvcMock.perform(get("/fire")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLFireDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress"); // mistyped parameter
            when(mockAlertsService.getURLFireDTO(
                    Objects.requireNonNull(
                            params.getFirst("address"))))
            .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/fire")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLFloodDTO
    @Nested
    @DisplayName("getURLFloodDTO()")
    class AlertsControllerGetURLFloodDTOTests {
        @Test
        void Given_validRequest_When_getURLFloodDTO_Then_statusIsOK() throws Exception {
            params.addAll("stationNumbers", Arrays.asList("2","5"));
            when(mockAlertsService.getURLFloodDTO(
                    Arrays.asList(2,5)))
                    .thenReturn(any(URLFloodDTO.class));
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLFloodDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.addAll("stationNumbers", Arrays.asList("2","5"));
            when(mockAlertsService.getURLFloodDTO(
                    Arrays.asList(2,5)))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLFloodDTO_Then_statusIsBadRequest() throws Exception {
            params.add("statioNumbers", "2"); // mistyped parameter
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_mismatchParameter_When_getURLFloodDTO_Then_statusIsBadRequest() {
            params.add("stationNumbers", "someString"); // String instead of int
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLFloodDTO_Then_statusIsServerError() throws Exception {
            params.addAll("stationNumbers", Arrays.asList("2","5"));
            when(mockAlertsService.getURLFloodDTO(
                    Arrays.asList(2,5)))
            .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLPersonInfoDTO
    @Nested
    @DisplayName("getURLPersonInfoDTO()")
    class AlertsControllerGetURLPersonInfoDTOTests {
        @Test
        void Given_validRequest_When_getURLPersonInfoDTO_Then_statusIsOK() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockAlertsService.getURLPersonInfoDTO(
                    Objects.requireNonNull(params.getFirst("firstName")),
                    Objects.requireNonNull(params.getFirst("lastName"))))
                    .thenReturn(any(URLPersonInfoDTO.class));
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLPersonInfoDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockAlertsService.getURLPersonInfoDTO(
                    Objects.requireNonNull(params.getFirst("firstName")),
                    Objects.requireNonNull(params.getFirst("lastName"))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLPersonInfoDTO_Then_statusIsBadRequest() throws Exception {
            params.add("firstName", "someFirstName");
            // lastName parameter is missing
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
        
        @Test
        void Given_daoError_When_getURLPersonInfoDTO_Then_statusIsServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");
            when(mockAlertsService.getURLPersonInfoDTO(
                    Objects.requireNonNull(params.getFirst("firstName")),
                    Objects.requireNonNull(params.getFirst("lastName"))))
                    .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ URLCommunityEmailDTO
    @Nested
    @DisplayName("getURLCommunityEmailDTO()")
    class AlertsControllerGetURLCommunityEmailDTOTests {
        @Test
        void Given_validRequest_When_getURLCommunityEmailDTO_Then_statusIsOK() throws Exception {
            params.add("city", "someCity");
            when(mockAlertsService.getURLCommunityEmailDTO(
                    Objects.requireNonNull(params.getFirst("city"))))
                    .thenReturn(any(URLCommunityEmailDTO.class));
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_validRequestButNoData_When_getURLCommunityEmailDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("city", "someCity");
            when(mockAlertsService.getURLCommunityEmailDTO(
                    Objects.requireNonNull(params.getFirst("city"))))
                    .thenReturn(null);
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }


        @Test
        void Given_missingParameter_When_getURLCommunityEmailDTO_Then_statusIsBadRequest() throws Exception {
            // no params
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        void Given_daoError_When_getURLCommunityEmailDTO_Then_statusIsServerError() throws Exception {
            params.add("city", "someCity");
            when(mockAlertsService.getURLCommunityEmailDTO(
                    Objects.requireNonNull(params.getFirst("city"))))
                    .thenThrow(new Exception());
            try {
                mvcMock.perform(get("/communityEmail")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }
}