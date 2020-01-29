package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonControllerTest.class)
class PersonControllerTest {

    @Autowired
    private PersonController personController;
    @MockBean
    private PersonService mockPersonService;

    //    ------------------------------------------------------------------------------ add
    @Test
    void Given_validRequest_When_add_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidRequest_When_add_Then_returnFalse() {
    }

    //    ------------------------------------------------------------------------------ update
    @Test
    void Given_validRequest_When_update_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidRequest_When_update_Then_returnFalse() {
    }

    //    ------------------------------------------------------------------------------ delete
    @Test
    void Given_validRequest_When_delete_Then_receiveAndPassParameters() {
    }

    @Test
    void Given_invalidRequest_When_delete_Then_returnFalse() {
    }

}