package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.services.AlertsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertsController.class)
@DisplayName("AlertsController Tests on :")
class AlertsControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean(reset = MockReset.BEFORE)
    private AlertsService mockAlertsService;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    //    ------------------------------------------------------------------------------ URLFirestationDTO

    @Nested
    @DisplayName("getURLFirestationDTO()")
    class AlertsControllerGetURLFirestationDTOTests {
        @Test
        void Given_validRequest_When_getURLFirestationDTO_Then_statusIsOK() throws Exception {
            params.add("stationNumber", "2");

            doReturn(new URLFirestationDTO(2, 2, new ArrayList<PersonAddressPhoneDTO>()))
                    .when(mockAlertsService).getURLFirestationDTO(2);

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }


        @Test
        void Given_validRequestButNoData_When_getURLFirestationDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("stationNumber", "2");

            doReturn(null)
                    .when(mockAlertsService).getURLFirestationDTO(2);

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() throws Exception {
            params.add("statioNumber", "2"); // mistyped parameter

            doReturn(null)
                    .when(mockAlertsService).getURLFirestationDTO(2);

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_mismatchParameter_When_getURLFirestationDTO_Then_statusIsBadRequest() throws Exception {
            params.add("stationNumber", "someString"); // String instead of int

            doReturn(null)
                    .when(mockAlertsService).getURLFirestationDTO(2);

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }

        @Test
        void Given_daoError_When_getURLFirestationDTO_Then_statusIsServerError() throws Exception {
            params.add("stationNumber", "2");

            when(mockAlertsService.getURLFirestationDTO(
                    2))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

//    ------------------------------------------------------------------------------ URLChildAlertDTO

    @Nested
    @DisplayName("getURLChildAlertDTO()")
    class AlertsControllerGetURLChildAlertDTOTests {
        @Test
        void Given_validRequest_When_getURLChildAlertDTO_Then_statusIsOK() throws Exception {
            params.add("address", "someAddress");

            doReturn(new URLChildAlertDTO(new ArrayList<ChildDTO>(), new ArrayList<ChildFamilyMemberDTO>()))
                    .when(mockAlertsService).getURLChildAlertDTO(params.getFirst("address"));

            mvcMock.perform(get("/childAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLChildAlertDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("address", "someAddress");

            doReturn(null)
                    .when(mockAlertsService).getURLChildAlertDTO(params.getFirst("address"));

            mvcMock.perform(get("/childAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLChildAlertDTO_Then_statusIsBadRequest() throws Exception {
            params.add("addres", "someAddress"); // mistyped parameter

            doReturn(null)
                    .when(mockAlertsService).getURLChildAlertDTO(params.getFirst("addres"));

            mvcMock.perform(get("/childAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLChildAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress");

            when(mockAlertsService.getURLChildAlertDTO(
                    params.getFirst("address")))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/childAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    //    ------------------------------------------------------------------------------ URLPhoneAlertDTO
    @Nested
    @DisplayName("getURLPhoneAlertDTO()")
    class AlertsControllerGetURLPhoneAlertDTOTests {
        @Test
        void Given_validRequest_When_getURLPhoneAlertDTO_Then_statusIsOK() throws Exception {
            params.add("firestation", "2");

            doReturn(new URLPhoneAlertDTO(new ArrayList<String>()))
                    .when(mockAlertsService).getURLPhoneAlertDTO(2);

            mvcMock.perform(get("/phoneAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLPhoneAlertDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("firestation", "2");

            doReturn(null)
                    .when(mockAlertsService).getURLPhoneAlertDTO(2);

            mvcMock.perform(get("/phoneAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLPhoneAlertDTO_Then_statusIsBadRequest() throws Exception {
            params.add("firestatio", "2"); // mistyped parameter

            doReturn(null)
                    .when(mockAlertsService).getURLPhoneAlertDTO(2);

            mvcMock.perform(get("/phoneAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_mismatchParameter_When_getURLPhoneAlertDTO_Then_statusIsBadRequest() throws Exception {
            params.add("firestation", "someString"); // String instead of int

            doReturn(null)
                    .when(mockAlertsService).getURLPhoneAlertDTO(2);
            mvcMock.perform(get("/phoneAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLPhoneAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("firestation", "2");

            when(mockAlertsService.getURLPhoneAlertDTO(2))
                    .thenThrow(Exception.class);

            mvcMock.perform(get("/phoneAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    //    ------------------------------------------------------------------------------ URLFireDTO
    @Nested
    @DisplayName("getURLFireDTO()")
    class AlertsControllerGetURLFireDTOTests {
        @Test
        void Given_validRequest_When_getURLFireDTO_Then_statusIsOK() throws Exception {
            params.add("address", "someAddress");

            doReturn(new URLFireDTO(2, new ArrayList<EndangeredPersonDTO>()))
                    .when(mockAlertsService).getURLFireDTO(params.getFirst("address"));

            mvcMock.perform(get("/fire")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLFireDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("address", "someAddress");

            doReturn(null)
                    .when(mockAlertsService).getURLFireDTO(params.getFirst("address"));

            mvcMock.perform(get("/fire")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLFireDTO_Then_statusIsBadRequest() throws Exception {
            params.add("addres", "someAddress"); // mistyped parameter

            doReturn(null)
                    .when(mockAlertsService).getURLFireDTO(params.getFirst("addres"));

            mvcMock.perform(get("/fire")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLFireDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress"); // mistyped parameter

            when(mockAlertsService.getURLFireDTO(
                    params.getFirst("address")))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/fire")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    //    ------------------------------------------------------------------------------ URLFloodDTO
    @Nested
    @DisplayName("getURLFloodDTO()")
    class AlertsControllerGetURLFloodDTOTests {
        @Test
        void Given_validRequest_When_getURLFloodDTO_Then_statusIsOK() throws Exception {
            params.add("stations", "2");
            params.add("stations", "5");

            doReturn(new URLFloodDTO(new HashMap<String, List<EndangeredPersonDTO>>()))
                    .when(mockAlertsService).getURLFloodDTO(Arrays.asList(2, 5));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLFloodDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("stations", "2");
            params.add("stations", "5");

            doReturn(null)
                    .when(mockAlertsService).getURLFloodDTO(Arrays.asList(2, 5));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLFloodDTO_Then_statusIsBadRequest() throws Exception {
            params.add("station", "2"); // mistyped parameter

            doReturn(null)
                    .when(mockAlertsService).getURLFloodDTO(Collections.singletonList(2));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_mismatchParameter_When_getURLFloodDTO_Then_statusIsBadRequest() throws Exception {
            params.add("stations", "someString"); // String instead of int

            doReturn(null)
                    .when(mockAlertsService).getURLFloodDTO(Collections.singletonList(anyInt()));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLFloodDTO_Then_statusIsServerError() throws Exception {
            params.add("stations", "2");
            params.add("stations", "5");

            when(mockAlertsService.getURLFloodDTO(
                    Arrays.asList(2, 5)))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
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

            doReturn(new URLPersonInfoDTO("firstName",
                    "lastName",
                    "address",
                    "age",
                    "email",
                    new ArrayList<String>(),
                    new ArrayList<String>()))
                    .when(mockAlertsService).getURLPersonInfoDTO(
                    params.getFirst("firstName"), params.getFirst("lastName"));

            mvcMock.perform(get("/personInfo")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLPersonInfoDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");

            doReturn(null)
                    .when(mockAlertsService)
                    .getURLPersonInfoDTO(params.getFirst("firstName"), params.getFirst("lastName"));

            mvcMock.perform(get("/personInfo")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLPersonInfoDTO_Then_statusIsBadRequest() throws Exception {
            params.add("firstName", "someFirstName");
            // lastName parameter is missing

            doReturn(null)
                    .when(mockAlertsService)
                    .getURLPersonInfoDTO(params.getFirst("firstName"), params.getFirst("lastName"));

            mvcMock.perform(get("/personInfo")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLPersonInfoDTO_Then_statusIsServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");

            when(mockAlertsService.getURLPersonInfoDTO(
                    params.getFirst("firstName"),
                    params.getFirst("lastName")))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/personInfo")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }

    //    ------------------------------------------------------------------------------ URLCommunityEmailDTO
    @Nested
    @DisplayName("getURLCommunityEmailDTO()")
    class AlertsControllerGetURLCommunityEmailDTOTests {
        @Test
        void Given_validRequest_When_getURLCommunityEmailDTO_Then_statusIsOK() throws Exception {
            params.add("city", "someCity");

            doReturn(new URLCommunityEmailDTO(new ArrayList<String>()))
                    .when(mockAlertsService).getURLCommunityEmailDTO(params.getFirst("city"));

            mvcMock.perform(get("/communityEmail")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }

        @Test
        void Given_validRequestButNoData_When_getURLCommunityEmailDTO_Then_returnNullAndStatusIsNoContent() throws Exception {
            params.add("city", "someCity");

            doReturn(null)
                    .when(mockAlertsService).getURLCommunityEmailDTO(params.getFirst("city"));

            mvcMock.perform(get("/communityEmail")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }


        @Test
        void Given_missingParameter_When_getURLCommunityEmailDTO_Then_statusIsBadRequest() throws Exception {
            // no params
            doReturn(new URLCommunityEmailDTO(new ArrayList<String>()))
                    .when(mockAlertsService).getURLCommunityEmailDTO(params.getFirst("city"));

            mvcMock.perform(get("/communityEmail")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void Given_daoError_When_getURLCommunityEmailDTO_Then_statusIsServerError() throws Exception {
            params.add("city", "someCity");

            when(mockAlertsService.getURLCommunityEmailDTO(
                    params.getFirst("city")))
                    .thenThrow(new Exception());

            mvcMock.perform(get("/communityEmail")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}