package com.safetynet.safetynetAlerts.daos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("MedicalRecordDAOJsonFile Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicalRecordDAOJsonFileTest {

    @MockBean
    private JsonFileDatabase mockJsonFileDatabase;

    @Autowired
    private MedicalRecordDAO medicalRecordDAO;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_addMedicalRecord_Then_returnTrue() {
            when(mockJsonFileDatabase.addMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")))
                    .thenReturn(true);
            assertTrue(medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_addMedicalRecord_Then_returnFalse() {
            when(mockJsonFileDatabase.addMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")))
                    .thenReturn(false);
            assertFalse(medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }
    }

    //    ------------------------------------------------------------------------------ UPDATE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("update()")
    class updateTestMethods {
        @Test
        void Given_validParameters_When_updateMedicalRecord_Then_returnTrue() {
            when(mockJsonFileDatabase.updateMedicalRecord(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()))
                    .thenReturn(true);
            assertTrue(medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_updateMedicalRecord_Then_returnFalse() {
            when(mockJsonFileDatabase.updateMedicalRecord(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()))
                    .thenReturn(false);
            assertFalse(medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()));
        }
    }

    //    ------------------------------------------------------------------------------ DELETE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class deleteTestMethods {
        @Test
        void Given_validParameters_When_deleteMedicalRecord_Then_returnTrue() {
            when(mockJsonFileDatabase.deleteMedicalRecord(
                    "firstName",
                    "lastName"))
                    .thenReturn(true);
            assertTrue(medicalRecordDAO.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deleteMedicalRecord_Then_returnFalse() {
            when(mockJsonFileDatabase.deleteMedicalRecord(
                    "firstName",
                    "lastName"))
                    .thenReturn(false);
            assertFalse(medicalRecordDAO.delete(
                    "firstName",
                    "lastName"));
        }
    }
}