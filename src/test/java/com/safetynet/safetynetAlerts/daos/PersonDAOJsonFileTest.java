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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("PersonDAOJsonFileTest Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonDAOJsonFileTest {

    @MockBean
    private JsonFileDatabase mockJsonFileDatabase;

    @Autowired
    private PersonDAO personDAO;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true)
                    .when(mockJsonFileDatabase).addPerson(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertTrue(personDAO.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city",
                    "zip",
                    "phone",
                    "email"));
        }

        @Test
        void Given_IllegalDataOverrideException_When_add_Then_throwsIllegalDataOverrideException() throws Exception {
            doThrow(new IllegalDataOverrideException())
                    .when(mockJsonFileDatabase).addPerson(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertThrows(IllegalDataOverrideException.class, () -> personDAO.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city",
                    "zip",
                    "phone",
                    "email"));
        }

        @Test
        void Given_IOException_When_add_Then_throwsIOException() throws Exception {
            doThrow(new IOException())
                    .when(mockJsonFileDatabase).addPerson(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertThrows(IOException.class, () -> personDAO.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city",
                    "zip",
                    "phone",
                    "email"));
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
                    .updatePerson(anyString(),
                            anyString(),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class));
            assertTrue(personDAO.update(
                    "firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.of("phone"),
                    Optional.empty()));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .updatePerson(anyString(),
                            anyString(),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class));
            assertThrows(NoSuchDataException.class,
                    () -> personDAO.update(
                            "firstName",
                            "lastName",
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of("phone"),
                            Optional.empty()));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .updatePerson(anyString(),
                            anyString(),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class),
                            any(Optional.class));
            assertThrows(IOException.class,
                    () -> personDAO.update(
                            "firstName",
                            "lastName",
                            Optional.empty(),
                            Optional.empty(),
                            Optional.empty(),
                            Optional.of("phone"),
                            Optional.empty()));
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
                    .deletePerson(anyString(), anyString());
            assertTrue(personDAO.delete("firstName", "lastName"));
        }

        @Test
        void Given_NoSuchDataException_When_delete_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .deletePerson(anyString(), anyString());
            assertThrows(NoSuchDataException.class,
                    () -> personDAO.delete("firstName", "lastName"));
        }

        @Test
        void Given_IOException_When_delete_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .deletePerson(anyString(), anyString());
            assertThrows(IOException.class,
                    () -> personDAO.delete("firstName", "lastName"));
        }
    }
}