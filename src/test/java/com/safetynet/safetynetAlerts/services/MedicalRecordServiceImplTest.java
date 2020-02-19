package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
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
@DisplayName("MedicalRecordService Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicalRecordServiceImplTest {

    @MockBean
    private MedicalRecordDAO mockMedicalRecordDAO;

    @Autowired
    private MedicalRecordService medicalRecordService;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_addMedicalRecord_Then_returnTrue() {
            when(mockMedicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")))
                    .thenReturn(true);
            assertTrue(medicalRecordService.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_addMedicalRecord_Then_returnFalse() {
            when(mockMedicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")))
                    .thenReturn(false);
            assertFalse(medicalRecordService.add(
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
            when(mockMedicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()))
                    .thenReturn(true);
            assertTrue(medicalRecordService.update(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_updateMedicalRecord_Then_returnFalse() {
            when(mockMedicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    Optional.of("birthDate"),
                    Optional.empty(),
                    Optional.empty()))
                    .thenReturn(false);
            assertFalse(medicalRecordService.update(
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
            when(mockMedicalRecordDAO.delete(
                    "firstName",
                    "lastName"))
                    .thenReturn(true);
            assertTrue(medicalRecordService.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deleteMedicalRecord_Then_returnFalse() {
            when(mockMedicalRecordDAO.delete(
                    "firstName",
                    "lastName"))
                    .thenReturn(false);
            assertFalse(medicalRecordService.delete(
                    "firstName",
                    "lastName"));
        }
    }
}