package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.AlertsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertsControllerTest.class)
class AlertsControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AlertsService mockAlertsService;

    //    ------------------------------------------------------------------------------ URLFirestationDTO
    @Test
    void Given_validRequest_When_getURLFirestationDTO_Then_returnAccordingInstance() {
    }

    @Test
    void Given_validRequestButNoData_When_getURLFirestationDTO_Then_returnEmptyInstance() {
    }

    @Test
    void Given_invalidRequest_When_getURLFirestationDTO_Then_throwsException() {
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