package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("FirestationService Tests on :")
class FirestationServiceImplTest {

    @MockBean
    private FirestationDAO mockFirestationDAO;

    @Autowired
    private FirestationService firestationService;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_addFirestation_Then_returnTrue() {
            when(mockFirestationDAO.add(
                    "address",
                    2))
                    .thenReturn(true);
            assertTrue(firestationService.add(
                    "address",
                    2));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_addFirestation_Then_returnFalse() {
            when(mockFirestationDAO.add(
                    "address",
                    2))
                    .thenReturn(false);
            assertFalse(firestationService.add(
                    "address",
                    2));
        }
    }

    //    ------------------------------------------------------------------------------ UPDATE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("update()")
    class updateTestMethods {
        @Test
        void Given_validParameters_When_updateFirestation_Then_returnTrue() {
            when(mockFirestationDAO.update(
                    "address",
                    2))
                    .thenReturn(true);
            assertTrue(firestationService.update(
                    "address",
                    2));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_updateFirestation_Then_returnFalse() {
            when(mockFirestationDAO.update(
                    "address",
                    2))
                    .thenReturn(false);
            assertFalse(firestationService.update(
                    "address",
                    2));
        }
    }

    //    ------------------------------------------------------------------------------ DELETE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class deleteTestMethods {
        @Test
        void Given_validParameters_When_deleteFirestationByAddress_Then_returnTrue() {
            when(mockFirestationDAO.delete(
                    "address"))
                    .thenReturn(true);
            assertTrue(firestationService.delete(
                    "address"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deleteFirestationByAddress_Then_returnFalse() {
            when(mockFirestationDAO.delete(
                    "address"))
                    .thenReturn(false);
            assertFalse(firestationService.delete(
                    "address"));
        }

        @Test
        void Given_validParameters_When_deleteFirestationByNumber_Then_returnTrue() {
            when(mockFirestationDAO.delete(
                    2))
                    .thenReturn(true);
            assertTrue(firestationService.delete(
                    2));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deleteFirestationByNumber_Then_returnFalse() {
            when(mockFirestationDAO.delete(
                    2))
                    .thenReturn(false);
            assertFalse(firestationService.delete(
                    2));
        }
    }
}