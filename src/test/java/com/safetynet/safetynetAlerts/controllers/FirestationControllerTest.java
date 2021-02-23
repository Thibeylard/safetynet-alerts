package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.services.FirestationService;
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

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(FirestationController.class)
@DisplayName("FirestationController tests on :")
class FirestationControllerTest {

    @Autowired
    private MockMvc mvcMock;
    @MockBean
    private FirestationService mockFirestationService;

    private MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeEach
    void resetMap() {
        params.clear();
    }

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class FirestationAddMethodTests {
        // ------------------------------------------------------------------------------ success
        @Test
        @DisplayName("Success case")
        void Given_validRequest_When_add_Then_statusIsCreated() throws Exception {
            params.add("address", "someAddress");
            params.add("stationNumber", "2");
            doReturn(true).when(mockFirestationService)
                    .add(anyString(), anyInt());
            try {
                mvcMock.perform(post("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ forbidden
        @Test
        @DisplayName("Illegal override case")
        void Given_illegalDataOverrideException_When_add_Then_statusIsForbidden() throws Exception {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            doThrow(new IllegalDataOverrideException())
                    .when(mockFirestationService)
                    .add(anyString(), anyInt());
            try {
                mvcMock.perform(post("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isForbidden());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @DisplayName("IO error case")
        void Given_IOException_When_add_Then_statusIsInternalServerError() throws Exception {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            doThrow(new IOException())
                    .when(mockFirestationService)
                    .add(anyString(), anyInt());
            try {
                mvcMock.perform(post("/firestation")
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
    class FirestationUpdateMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @DisplayName("Success case")
        void Given_validRequest_When_update_Then_statusIsOK() throws Exception {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            doReturn(true).when(mockFirestationService)
                    .update(anyString(), anyInt());
            try {
                mvcMock.perform(put("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ not found
        @Test
        @DisplayName("Not found case")
        void Given_noSuchDataException_When_update_Then_statusIsNotFound() throws Exception {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            doThrow(new NoSuchDataException())
                    .when(mockFirestationService)
                    .update(anyString(), anyInt());
            try {
                mvcMock.perform(put("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @DisplayName("IO error case")
        void Given_IOExceptionThrown_When_update_Then_statusIsInternalServerError() throws Exception {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            doThrow(new IOException())
                    .when(mockFirestationService)
                    .update(anyString(), anyInt());
            try {
                mvcMock.perform(put("/firestation")
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
    class FirestationDeleteMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @DisplayName("By number : Success case")
        void Given_validRequest_When_deleteByNumber_Then_statusIsOK() throws Exception {
            params.add("stationNumber", "2");
            doReturn(true).when(mockFirestationService)
                    .delete(anyInt());
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
        @DisplayName("By address : Success case")
        void Given_validRequest_When_deleteByAddress_Then_statusIsOK() throws Exception {
            params.add("address", "someAddress");
            doReturn(true).when(mockFirestationService)
                    .delete(anyString());
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ not found
        @Test
        @DisplayName("By number : Not found case")
        void Given_noSuchDataException_When_deleteByNumber_Then_statusIsNotFound() throws Exception {
            params.add("stationNumber", "2");
            doThrow(new NoSuchDataException())
                    .when(mockFirestationService)
                    .delete(anyInt());
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @DisplayName("By address : Not found case")
        void Given_noSuchDataException_When_deleteByAddress_Then_statusIsNotFound() throws Exception {
            params.add("address", "someAddress");
            doThrow(new NoSuchDataException())
                    .when(mockFirestationService)
                    .delete(anyString());
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @DisplayName("By number : IO error case")
        void Given_IOException_When_deleteByNumber_Then_statusIsInternalServerError() throws Exception {
            params.add("stationNumber", "2");
            doThrow(new IOException())
                    .when(mockFirestationService)
                    .delete(anyInt());
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @DisplayName("By address : IO error case")
        void Given_IOException_When_deleteByAddress_Then_statusIsInternalServerError() throws Exception {
            params.add("address", "someAddress");
            doThrow(new IOException())
                    .when(mockFirestationService)
                    .delete(anyString());
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isInternalServerError());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }
    }

    //    ------------------------------------------------------------------------------ GET
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("get()")
    class FirestationGetMethodTests {
        //    ------------------------------------------------------------------------------ success
        @Test
        @DisplayName("Success case")
        void Given_validRequest_When_get_Then_statusIsOk() throws Exception {
            Firestation response = FirestationFactory.createFirestation();
            params.add("address", "someAddress");
            doReturn(response).when(mockFirestationService)
                    .get(anyString());
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ not found
        @Test
        @DisplayName("Not found case")
        void Given_NoSuchDataException_When_get_Then_statusIsNotFound() throws Exception {
            params.add("address", "someAddress");
            doThrow(new NoSuchDataException())
                    .when(mockFirestationService)
                    .get(anyString());
            try {
                mvcMock.perform(get("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNotFound());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        //    ------------------------------------------------------------------------------ server error
        @Test
        @DisplayName("IO error case")
        void Given_IOException_When_get_Then_statusIsInternalServerError() throws Exception {
            params.add("address", "someAddress");
            doThrow(new IOException())
                    .when(mockFirestationService)
                    .get(anyString());
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
}