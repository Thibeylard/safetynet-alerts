package com.safetynet.safetynetAlerts.daos;

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
        @DisplayName("Success case")
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase).addMedicalRecord(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertTrue(medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        @DisplayName("Illegal override case")
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException()).when(mockJsonFileDatabase).addMedicalRecord(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertThrows(IllegalDataOverrideException.class, () -> medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        @DisplayName("IO error case")
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).addMedicalRecord(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyList(),
                    anyList());
            assertThrows(IOException.class, () -> medicalRecordDAO.add(
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
        @DisplayName("Success case")
        void Given_validParameters_When_update_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase)
                    .updateMedicalRecord(
                            anyString(),
                            anyString(),
                            nullable(String.class),
                            nullable(List.class),
                            nullable(List.class));
            assertTrue(medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        @DisplayName("Not found case")
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase).updateMedicalRecord(
                    anyString(),
                    anyString(),
                    nullable(String.class),
                    nullable(List.class),
                    nullable(List.class));
            assertThrows(NoSuchDataException.class, () -> medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        @DisplayName("IO error case")
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).updateMedicalRecord(
                    nullable(String.class),
                    nullable(String.class),
                    nullable(String.class),
                    nullable(List.class),
                    nullable(List.class));
            assertThrows(IOException.class, () -> medicalRecordDAO.update(
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
        @DisplayName("Success case")
        void Given_validParameters_When_delete_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase)
                    .deleteMedicalRecord(
                            anyString(),
                            anyString());
            assertTrue(medicalRecordDAO.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        @DisplayName("Not found case")
        void Given_NoSuchDataException_When_delete_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .deleteMedicalRecord(
                            anyString(),
                            anyString());
            assertThrows(NoSuchDataException.class, () -> medicalRecordDAO
                    .delete("firstName", "lastName"));
        }

        @Test
        @DisplayName("IO error case")
        void Given_IOException_When_delete_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .deleteMedicalRecord(
                            anyString(),
                            anyString());
            assertThrows(IOException.class, () -> medicalRecordDAO
                    .delete("firstName", "lastName"));
        }
    }
}