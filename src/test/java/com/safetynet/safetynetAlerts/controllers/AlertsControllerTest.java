package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.Person;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        void Given_NoMedicalRecordExceptionThrown_When_getURLFirestationDTO_Then_statusIsNotFound() throws Exception {
            params.add("stationNumber", "2");

            doThrow(new NoMedicalRecordException("Bob", "Hawkins"))
                    .when(mockAlertsService)
                    .getURLFirestationDTO(2);

            mvcMock.perform(get("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFirestationDTO_Then_statusIsServerError() throws Exception {
            params.add("stationNumber", "2");

            doThrow(new IOException())
                    .when(mockAlertsService)
                    .getURLFirestationDTO(2);

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
        void Given_NoMedicalRecordExceptionThrown_When_getURLChildAlertDTO_Then_statusIsNotFound() throws Exception {
            params.add("address", "someAddress");

            doThrow(new NoMedicalRecordException("Bob", "Hawkins"))
                    .when(mockAlertsService)
                    .getURLChildAlertDTO(params.getFirst("address"));

            mvcMock.perform(get("/childAlert")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void Given_IOExceptionThrown_When_getURLChildAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress");

            doThrow(new IOException())
                    .when(mockAlertsService)
                    .getURLChildAlertDTO(params.getFirst("address"));

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

            doReturn(new URLPhoneAlertDTO(new ArrayList<>()))
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
        void Given_IOExceptionThrown_When_getURLPhoneAlertDTO_Then_statusIsServerError() throws Exception {
            params.add("firestation", "2");

            when(mockAlertsService.getURLPhoneAlertDTO(2))
                    .thenThrow(new IOException());

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
        void Given_NoMedicalRecordExceptionThrown_When_getURLFireDTO_Then_statusIsNotFound() throws Exception {
            params.add("address", "someAddress"); // mistyped parameter

            doThrow(new NoMedicalRecordException("Bob", "Hawkins"))
                    .when(mockAlertsService)
                    .getURLFireDTO(params.getFirst("address"));

            mvcMock.perform(get("/fire")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFireDTO_Then_statusIsServerError() throws Exception {
            params.add("address", "someAddress"); // mistyped parameter

            doThrow(new IOException())
                    .when(mockAlertsService)
                    .getURLFireDTO(params.getFirst("address"));

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

            doReturn(new URLFloodDTO(new HashMap<>()))
                    .when(mockAlertsService).getURLFloodDTO(List.of(2, 5));

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
                    .when(mockAlertsService).getURLFloodDTO(List.of(2, 5));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }

        @Test
        void Given_NoMedicalRecordExceptionThrown_When_getURLFloodDTO_Then_statusIsNotFound() throws Exception {
            params.add("stations", "2");
            params.add("stations", "5");

            doThrow(new NoMedicalRecordException("Bob", "Hawkins"))
                    .when(mockAlertsService)
                    .getURLFloodDTO(List.of(2, 5));

            mvcMock.perform(get("/flood/stations")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFloodDTO_Then_statusIsServerError() throws Exception {
            params.add("stations", "2");
            params.add("stations", "5");

            doThrow(new IOException())
                    .when(mockAlertsService)
                    .getURLFloodDTO(List.of(2, 5));

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
        void Given_NoMedicalRecordExceptionThrown_When_getURLPersonInfoDTO_Then_statusIsNotFound() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");

            doThrow(new NoMedicalRecordException("Bob", "Hawkins"))
                    .when(mockAlertsService)
                    .getURLPersonInfoDTO(params.getFirst("firstName"), params.getFirst("lastName"));

            mvcMock.perform(get("/personInfo")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        void Given_IOExceptionThrown_When_getURLPersonInfoDTO_Then_statusIsServerError() throws Exception {
            params.add("firstName", "someFirstName");
            params.add("lastName", "someLastName");

            doThrow(new IOException())
                    .when(mockAlertsService)
                    .getURLPersonInfoDTO(params.getFirst("firstName"), params.getFirst("lastName"));

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

            doReturn(new URLCommunityEmailDTO().withPersonsEmails(new ArrayList<Person>()))
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
        void Given_IOExceptionThrown_When_getURLCommunityEmailDTO_Then_statusIsServerError() throws Exception {
            params.add("city", "someCity");

            when(mockAlertsService.getURLCommunityEmailDTO(
                    params.getFirst("city")))
                    .thenThrow(new IOException());

            mvcMock.perform(get("/communityEmail")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}