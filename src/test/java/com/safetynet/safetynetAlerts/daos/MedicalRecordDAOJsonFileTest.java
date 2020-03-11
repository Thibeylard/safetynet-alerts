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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase).addMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies"));
            assertTrue(medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException()).when(mockJsonFileDatabase).addMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies"));
            assertThrows(IllegalDataOverrideException.class, () -> medicalRecordDAO.add(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies")));
        }

        @Test
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).addMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    Collections.singletonList("medications"),
                    Collections.singletonList("allergies"));
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
        void Given_validParameters_When_update_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase)
                    .updateMedicalRecord(
                            "firstName",
                            "lastName",
                            "birthDate",
                            null,
                            null);
            assertTrue(medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase).updateMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null);
            assertThrows(NoSuchDataException.class, () -> medicalRecordDAO.update(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).updateMedicalRecord(
                    "firstName",
                    "lastName",
                    "birthDate",
                    null,
                    null);
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
        void Given_validParameters_When_delete_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase)
                    .deleteMedicalRecord("firstName", "lastName");
            assertTrue(medicalRecordDAO.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_NoSuchDataException_When_delete_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .deleteMedicalRecord("firstName", "lastName");
            assertThrows(NoSuchDataException.class, () -> medicalRecordDAO
                    .delete("firstName", "lastName"));
        }

        @Test
        void Given_IOException_When_delete_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .deleteMedicalRecord("firstName", "lastName");
            assertThrows(IOException.class, () -> medicalRecordDAO
                    .delete("firstName", "lastName"));
        }
    }
}