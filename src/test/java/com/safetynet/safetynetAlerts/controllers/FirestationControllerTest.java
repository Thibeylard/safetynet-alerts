package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
        @Tag("SuccessStatus")
        @DisplayName("add_Success")
        void Given_validRequest_When_add_Then_statusIsCreated() {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            try {
                mvcMock.perform(post("/firestation")
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
        void Given_missingParameter_When_add_Then_statusIsBadRequest() {
            params.add("stationNumber", "2");
            // No required parameter address
            try {
                mvcMock.perform(post("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @Tag("BadRequestStatus")
        @DisplayName("add_MismatchParameter_BadRequest")
        void Given_mismatchParameter_When_add_Then_statusIsBadRequest() {
            params.add("stationNumber", "someString");
            params.add("address", "someAddress");
            try {
                mvcMock.perform(post("/firestation")
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
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            when(mockFirestationService.add(anyString(), anyInt())).thenReturn(false);
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
        @Tag("SuccessStatus")
        @DisplayName("update_Success")
        void Given_validRequest_When_update_Then_statusIsNoContent() {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            try {
                mvcMock.perform(put("/firestation")
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
        @DisplayName("update_MissingParameter_BadRequest")
        void Given_missingParameter_When_update_Then_statusIsBadRequest() {
            params.add("stationNumber", "2");
            try {
                mvcMock.perform(put("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @Tag("BadRequestStatus")
        @DisplayName("update_MismatchParameter_BadRequest")
        void Given_mismatchParameter_When_update_Then_statusIsBadRequest() {
            params.add("stationNumber", "someString");
            params.add("address", "someAddress");
            try {
                mvcMock.perform(put("/firestation")
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
        @DisplayName("update_ServerError")
        void Given_validRequestButServiceNotWorking_When_update_Then_statusIsInternalServerError() {
            params.add("stationNumber", "2");
            params.add("address", "someAddress");
            when(mockFirestationService.update(anyString(), anyInt())).thenReturn(false);
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
        @Tag("SuccessStatus")
        @DisplayName("deleteByName_Success")
        void Given_validRequest_When_deleteByNumber_Then_statusIsNoContent() {
            params.add("stationNumber", "2");
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @Tag("SuccessStatus")
        @DisplayName("deleteByAddress_Success")
        void Given_validRequest_When_deleteByAddress_Then_statusIsNoContent() {
            params.add("address", "someAddress");
            try {
                mvcMock.perform(delete("/firestation")
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
        void Given_missingParameter_When_deleteByNumber_Then_statusIsBadRequest() {
            // No params
            try {
                mvcMock.perform(delete("/firestation")
                        .params(params)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest());
            } catch (Exception e) {
                fail("An exception was thrown");
            }
        }

        @Test
        @Tag("BadRequestStatus")
        @DisplayName("deleteByNumber_MismatchParameter_BadRequest")
        void Given_mismatchParameter_When_deleteByNumber_Then_statusIsBadRequest() {
            params.add("stationNumber", "someString"); // stationNumber requires int
            try {
                mvcMock.perform(delete("/firestation")
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
        @DisplayName("deleteByNumber_ServerError")
        void Given_validRequestButServiceNotWorking_When_deleteByNumber_Then_statusIsInternalServerError() {
            params.add("stationNumber", "2");
            when(mockFirestationService.delete(2)).thenReturn(false);
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
        @Tag("ServerErrorStatus")
        @DisplayName("deleteByAddress_ServerError")
        void Given_validRequestButServiceNotWorking_When_deleteByAddress_Then_statusIsInternalServerError() {
            params.add("address", "someAddress");
            when(mockFirestationService.delete("someAddress")).thenReturn(false);
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

}