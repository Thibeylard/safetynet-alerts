package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("FirestationService Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true).when(mockFirestationDAO).add(
                    anyString(),
                    anyInt());
            assertTrue(firestationService.add(
                    "address",
                    2));
        }

        @Test
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException()).when(mockFirestationDAO).add(
                    anyString(),
                    anyInt());
            assertThrows(IllegalDataOverrideException.class, () -> firestationService.add(
                    "address",
                    2));
        }

        @Test
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockFirestationDAO).add(
                    anyString(),
                    anyInt());
            assertThrows(IOException.class, () -> firestationService.add(
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
        void Given_validParameters_When_update_Then_returnTrue() throws Exception {
            doReturn(true).when(mockFirestationDAO).update(
                    anyString(),
                    anyInt());
            assertTrue(firestationService.update(
                    "address",
                    2));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO).update(
                    anyString(),
                    anyInt());
            assertThrows(NoSuchDataException.class, () -> firestationService.update(
                    "address",
                    2));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockFirestationDAO).update(
                    anyString(),
                    anyInt());
            assertThrows(IOException.class, () -> firestationService.update(
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
        void Given_validParameters_When_deleteByAddress_Then_returnTrue() throws Exception {
            doReturn(true).when(mockFirestationDAO)
                    .delete(anyString());
            assertTrue(firestationService.delete(
                    "address"));
        }

        @Test
        void Given_NoSuchDataException_When_deleteByAddress_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO)
                    .delete(anyString());
            assertThrows(NoSuchDataException.class, () -> firestationService.delete(
                    "address"));
        }

        @Test
        void Given_IOException_When_deleteByAddress_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockFirestationDAO)
                    .delete(anyString());
            assertThrows(IOException.class, () -> firestationService.delete(
                    "address"));
        }

        @Test
        void Given_validParameters_When_deleteByNumber_Then_returnTrue() throws Exception {
            doReturn(true).when(mockFirestationDAO)
                    .delete(anyInt());
            assertTrue(firestationService.delete(
                    2));
        }

        @Test
        void Given_NoSuchDataException_When_deleteByNumber_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO)
                    .delete(anyInt());
            assertThrows(NoSuchDataException.class, () -> firestationService.delete(
                    2));
        }

        @Test
        void Given_IOException_When_deleteByNumber_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockFirestationDAO)
                    .delete(anyInt());
            assertThrows(IOException.class, () -> firestationService.delete(
                    2));
        }
    }
}