package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.PersonDAO;
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
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("PersonService Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PersonServiceImplTest {

    @MockBean
    private PersonDAO mockPersonDAO;

    @Autowired
    private PersonService personService;

    //    ------------------------------------------------------------------------------ ADD
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("add()")
    class addTestMethods {
        @Test
        void Given_validParameters_When_add_Then_returnTrue() throws Exception {
            doReturn(true)
                    .when(mockPersonDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertTrue(personService.add(
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
                    .when(mockPersonDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertThrows(IllegalDataOverrideException.class, () -> personService.add(
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
                    .when(mockPersonDAO).add(
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString(),
                    anyString());
            assertThrows(IOException.class, () -> personService.add(
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
            doReturn(true).when(mockPersonDAO)
                    .update(anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertTrue(personService.update(
                    "firstName",
                    "lastName",
                    null,
                    null,
                    null,
                    "phone",
                    null));
        }

        @Test
        void Given_NoSuchDataException_When_update_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockPersonDAO)
                    .update(anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertThrows(NoSuchDataException.class,
                    () -> personService.update(
                            "firstName",
                            "lastName",
                            null,
                            null,
                            null,
                            "phone",
                            null));
        }

        @Test
        void Given_IOException_When_update_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockPersonDAO)
                    .update(anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertThrows(IOException.class,
                    () -> personService.update(
                            "firstName",
                            "lastName",
                            null,
                            null,
                            null,
                            "phone",
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
            doReturn(true).when(mockPersonDAO)
                    .delete(anyString(), anyString());
            assertTrue(personService.delete("firstName", "lastName"));
        }

        @Test
        void Given_NoSuchDataException_When_delete_Then_throwsNoSuchDataException() throws Exception {
            doThrow(new NoSuchDataException()).when(mockPersonDAO)
                    .delete(anyString(), anyString());
            assertThrows(NoSuchDataException.class,
                    () -> personService.delete("firstName", "lastName"));
        }

        @Test
        void Given_IOException_When_delete_Then_throwsIOException() throws Exception {
            doThrow(new IOException()).when(mockPersonDAO)
                    .delete(anyString(), anyString());
            assertThrows(IOException.class,
                    () -> personService.delete("firstName", "lastName"));
        }
    }
}
    