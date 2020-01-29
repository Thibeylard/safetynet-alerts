package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FirestationController.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean
    private FirestationService mockFirestationService;

    //    ------------------------------------------------------------------------------ add
    @Test
    void Given_validRequest_When_add_Then_statusIsOK() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("stationNumber","2");
        params.add("address","test");
        try {
            mvcMock.perform(post("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    @Test
    void Given_invalidRequest_When_add_Then_statusIsBadRequest() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("stationNumber","2");
        try {
            mvcMock.perform(post("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    //    ------------------------------------------------------------------------------ update
    @Test
    void Given_validRequest_When_update_Then_statusIsOK() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("stationNumber","2");
        params.add("address","anyAddress");
        try {
            mvcMock.perform(put("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    @Test
    void Given_invalidRequest_When_update_Then_statusIsBadRequest() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("stationNumber","2");
        try {
            mvcMock.perform(post("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    //    ------------------------------------------------------------------------------ delete
    @Test
    void Given_validRequest_When_deleteByNumber_Then_statusIsOK() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("stationNumber","2");
        try {
            mvcMock.perform(delete("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    @Test
    void Given_validRequest_When_deleteByAddress_Then_statusIsOK() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("address","anyAddress");
        try {
            mvcMock.perform(delete("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }

    @Test
    void Given_invalidRequest_When_delete_Then_statusIsBadRequest() {
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("statioNumber","2");
        try {
            mvcMock.perform(delete("/firestation")
                    .params(params)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail("An exception was thrown");
        }
    }
}