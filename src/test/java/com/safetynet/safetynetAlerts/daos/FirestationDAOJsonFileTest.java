package com.safetynet.safetynetAlerts.daos;

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
@DisplayName("FirestationDAOJsonFile Tests on :")
class FirestationDAOJsonFileTest {

    @MockBean
    private JsonFileDatabase mockJsonFileDatabase;

    @Autowired
    private FirestationDAO firestationDAO;

    //    --------------------------------------------------------------------------------- ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_addFirestation_Then_returnTrue() {
            when(mockJsonFileDatabase.addFirestation(
                    "address",
                    2))
                    .thenReturn(true);
            assertTrue(firestationDAO.add(
                    "address",
                    2));
        }

        @Test
        void Given_validParametersButDatabaseErrorOccurs_When_addFirestation_Then_returnFalse() {
            when(mockJsonFileDatabase.addFirestation(
                    "address",
                    2))
                    .thenReturn(false);
            assertFalse(firestationDAO.add(
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
            when(mockJsonFileDatabase.updateFirestation(
                    "address",
                    2))
                    .thenReturn(true);
            assertTrue(firestationDAO.update(
                    "address",
                    2));
        }

        @Test
        void Given_validParametersButDatabaseErrorOccurs_When_updateFirestation_Then_returnFalse() {
            when(mockJsonFileDatabase.updateFirestation(
                    "address",
                    2))
                    .thenReturn(false);
            assertFalse(firestationDAO.update(
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
            when(mockJsonFileDatabase.deleteFirestation(
                    "address"))
                    .thenReturn(true);
            assertTrue(firestationDAO.delete(
                    "address"));
        }

        @Test
        void Given_validParametersButDatabaseErrorOccurs_When_deleteFirestationByAddress_Then_returnFalse() {
            when(mockJsonFileDatabase.deleteFirestation(
                    "address"))
                    .thenReturn(false);
            assertFalse(firestationDAO.delete(
                    "address"));
        }

        @Test
        void Given_validParameters_When_deleteFirestationByNumber_Then_returnTrue() {
            when(mockJsonFileDatabase.deleteFirestation(
                    2))
                    .thenReturn(true);
            assertTrue(firestationDAO.delete(
                    2));
        }

        @Test
        void Given_validParametersButDatabaseErrorOccurs_When_deleteFirestationByNumber_Then_returnFalse() {
            when(mockJsonFileDatabase.deleteFirestation(
                    2))
                    .thenReturn(false);
            assertFalse(firestationDAO.delete(
                    2));
        }
    }
}