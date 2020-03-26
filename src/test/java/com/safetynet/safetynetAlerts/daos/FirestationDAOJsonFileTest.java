package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Firestation;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("FirestationDAOJsonFile Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FirestationDAOJsonFileTest {


    @MockBean
    private JsonFileDatabase mockJsonFileDatabase;

    @Autowired
    private FirestationDAO firestationDAO;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase).addFirestation(
                    anyString(),
                    anyInt());
            assertTrue(firestationDAO.add(
                    "address",
                    2));
        }

        @Test
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException()).when(mockJsonFileDatabase).addFirestation(
                    anyString(),
                    anyInt());
            assertThrows(IllegalDataOverrideException.class, () -> firestationDAO.add(
                    "address",
                    2));
        }

        @Test
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).addFirestation(
                    anyString(),
                    anyInt());
            assertThrows(IOException.class, () -> firestationDAO.add(
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
            doReturn(true).when(mockJsonFileDatabase).updateFirestation(
                    anyString(),
                    anyInt());
            assertTrue(firestationDAO.update(
                    "address",
                    2));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase).updateFirestation(
                    anyString(),
                    anyInt());
            assertThrows(NoSuchDataException.class, () -> firestationDAO.update(
                    "address",
                    2));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase).updateFirestation(
                    anyString(),
                    anyInt());
            assertThrows(IOException.class, () -> firestationDAO.update(
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
            doReturn(true).when(mockJsonFileDatabase)
                    .deleteFirestation(anyString());
            assertTrue(firestationDAO.delete(
                    "address"));
        }

        @Test
        void Given_NoSuchDataException_When_deleteByAddress_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .deleteFirestation(anyString());
            assertThrows(NoSuchDataException.class, () -> firestationDAO.delete(
                    "address"));
        }

        @Test
        void Given_IOException_When_deleteByAddress_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .deleteFirestation(anyString());
            assertThrows(IOException.class, () -> firestationDAO.delete(
                    "address"));
        }

        @Test
        void Given_validParameters_When_deleteByNumber_Then_returnTrue() throws Exception {
            doReturn(true).when(mockJsonFileDatabase)
                    .deleteFirestation(anyInt());
            assertTrue(firestationDAO.delete(
                    2));
        }

        @Test
        void Given_NoSuchDataException_When_deleteByNumber_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .deleteFirestation(anyInt());
            assertThrows(NoSuchDataException.class, () -> firestationDAO.delete(
                    2));
        }

        @Test
        void Given_IOException_When_deleteByNumber_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .deleteFirestation(anyInt());
            assertThrows(IOException.class, () -> firestationDAO.delete(
                    2));
        }
    }

    //    ------------------------------------------------------------------------------ GET
    //    -------------------------------------------------------------------------------------

    @Nested
    @DisplayName("get...() ")
    class getTestMethods {
        @Nested
        @DisplayName("getFirestation() ")
        class getFirestationTestMethods {
            @Test
            void Given_validParameters_When_getFirestation_Then_returnFirestation() throws IOException, NoSuchDataException {
                Firestation firestation = FirestationFactory.createFirestation();

                doReturn(firestation).when(mockJsonFileDatabase)
                        .getFirestation(anyString());

                assertThat(firestationDAO.getFirestation(Addresses.FIFTHROAD.getName()))
                        .isNotNull()
                        .usingRecursiveComparison()
                        .isEqualTo(firestation);
            }

            @Test
            void Given_NoSuchDataException_When_getFirestation_Then_throwsNoSuchDataException() throws NoSuchDataException {
                doThrow(new NoSuchDataException())
                        .when(mockJsonFileDatabase)
                        .getFirestation(anyString());

                assertThrows(NoSuchDataException.class,
                        () -> firestationDAO.getFirestation(Addresses.FIFTHROAD.getName()));
            }
        }

        @Nested
        @DisplayName("getFirestations() ")
        class getFirestationsTestMethods {
            @Test
            void Given_validParameters_When_getFirestations_Then_returnFirestation() throws IOException, NoSuchDataException {
                List<Firestation> firestations = FirestationFactory.createFirestations(2, null);

                doReturn(firestations).when(mockJsonFileDatabase)
                        .getFirestations(anyInt());

                assertThat(firestationDAO.getFirestations(1))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(firestations);
            }

            @Test
            void Given_NoSuchDataException_When_getFirestations_Then_throwsNoSuchDataException() throws NoSuchDataException {
                doThrow(new NoSuchDataException())
                        .when(mockJsonFileDatabase)
                        .getFirestation(anyString());

                assertThrows(NoSuchDataException.class,
                        () -> firestationDAO.getFirestation(Addresses.FIFTHROAD.getName()));
            }

        }
    }
}