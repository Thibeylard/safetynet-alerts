package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

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
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true).when(mockMedicalRecordDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertTrue(medicalRecordService.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException()).when(mockMedicalRecordDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertThrows(IllegalDataOverrideException.class, () -> medicalRecordService.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockMedicalRecordDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertThrows(IOException.class, () -> medicalRecordService.add(
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
        void Given_validParameters_When_update_Then_returnTrue() throws Exception {
            doReturn(true).when(mockMedicalRecordDAO)
                    .update(
                            anyString(),
                            anyString(),
                            nullable(String.class),
                            nullable(List.class),
                            nullable(List.class));
            assertTrue(medicalRecordService.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockMedicalRecordDAO)
                    .update(
                            anyString(),
                            anyString(),
                            nullable(String.class),
                            nullable(List.class),
                            nullable(List.class));
            assertThrows(NoSuchDataException.class, () -> medicalRecordService.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockMedicalRecordDAO).update(
                    anyString(),
                    anyString(),
                    nullable(String.class),
                    nullable(List.class),
                    nullable(List.class));
            assertThrows(IOException.class, () -> medicalRecordService.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }
    }

    //    ------------------------------------------------------------------------------ DELETE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class deleteTestMethods {
        @Test
        void Given_validParameters_When_delete_Then_returnTrue() throws Exception {
            doReturn(true).when(mockMedicalRecordDAO)
                    .delete(anyString(),
                            anyString());
            assertTrue(medicalRecordService.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_NoSuchDataException_When_delete_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockMedicalRecordDAO)
                    .delete(anyString(),
                            anyString());
            assertThrows(NoSuchDataException.class, () -> medicalRecordService
                    .delete("firstName", "lastName"));
        }

        @Test
        void Given_IOException_When_delete_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockMedicalRecordDAO)
                    .delete(anyString(),
                            anyString());
            assertThrows(IOException.class, () -> medicalRecordService
                    .delete("firstName", "lastName"));
        }
    }
}