package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.URLFirestationDTO;
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
        void Given_validRequest_When_getURLFirestationDTO_Then_statusIsOK() {
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
        void Given_validRequestButNoData_When_getURLFirestationDTO_Then_returnNullAndStatusIsNoContent() {
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
        void Given_missingParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() {
            params.add("statioNumber", "2"); // mistyped parameter
            when(mockAlertsService.getURLFirestationDTO(
                    Integer.parseInt(Objects.requireNonNull(params.getFirst("statioNumber")))))
                    .thenReturn(any(URLFirestationDTO.class));
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
        void Given_mismatchParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() {
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
        void Given_daoError_When_getURLFirestationDTO_Then_statusIsServerError() {
            params.add("stationNumber", "2"); // mistyped parameter
            when(mockAlertsService.getURLFirestationDTO(
                        Integer.parseInt(
                                Objects.requireNonNull(
                                        params.getFirst("stationNumber"))))
                ).thenThrow(new Exception());
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
    @Test
    void Given_validRequest_When_getURLChildAlertDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLChildAlertDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLChildAlertDTO_Then_throwsException() {
    }

    //    ------------------------------------------------------------------------------ URLPhoneAlertDTO
    @Test
    void Given_validRequest_When_getURLPhoneAlertDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLPhoneAlertDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLPhoneAlertDTO_Then_throwsException() {
    }

    //    ------------------------------------------------------------------------------ URLFireDTO
    @Test
    void Given_validRequest_When_getURLFireDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLFireDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLFireDTO_Then_throwsException() {
    }

    //    ------------------------------------------------------------------------------ URLFloodDTO
    @Test
    void Given_validRequest_When_getURLFloodDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLFloodDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLFloodDTO_Then_throwsException() {
    }

    //    ------------------------------------------------------------------------------ URLPersonInfoDTO
    @Test
    void Given_validRequest_When_getURLPersonInfoDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLPersonInfoDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLPersonInfoDTO_Then_throwsException() {
    }

    //    ------------------------------------------------------------------------------ URLCommunityEmailDTO
    @Test
    void Given_validRequest_When_getURLCommunityEmailDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLCommunityEmailDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLCommunityEmailDTO_Then_throwsException() {
    }
}