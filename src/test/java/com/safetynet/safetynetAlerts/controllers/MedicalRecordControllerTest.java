package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicalRecordControllerTest.class)
class MedicalRecordControllerTest {

    @Autowired
    private MedicalRecordController medicalRecordController;
    @MockBean
    private MedicalRecordService mockMedicalRecordService;

    //    ------------------------------------------------------------------------------ add
    @Test
    void Given_validEntry_When_add_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidEntry_When_add_Then_returnFalse() {
    }

    //    ------------------------------------------------------------------------------ update
    @Test
    void Given_validEntry_When_update_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidEntry_When_update_Then_returnFalse() {
    }

    //    ------------------------------------------------------------------------------ delete
    @Test
    void Given_validEntry_When_delete_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidEntry_When_delete_Then_returnFalse() {
    }

}