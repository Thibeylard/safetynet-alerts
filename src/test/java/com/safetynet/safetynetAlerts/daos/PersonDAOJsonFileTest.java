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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        void Given_validParameters_When_addPerson_Then_returnTrue() {
            when(mockJsonFileDatabase.addPerson("firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"))
                    .thenReturn(true);
            assertTrue(personDAO.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_addPerson_Then_returnFalse() {
            when(mockJsonFileDatabase.addPerson("firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"))
                    .thenReturn(false);
            assertFalse(personDAO.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city", "zip",
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
        void Given_validParameters_When_updatePerson_Then_returnTrue() {
            when(mockJsonFileDatabase.updatePerson("firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()))
                    .thenReturn(true);
            assertTrue(personDAO.update(
                    "firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_updatePerson_Then_returnFalse() {
            when(mockJsonFileDatabase.updatePerson("firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()))
                    .thenReturn(false);
            assertFalse(personDAO.update(
                    "firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()));
        }
    }

    //    ------------------------------------------------------------------------------ DELETE
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("delete()")
    class deleteTestMethods {
        @Test
        void Given_validParameters_When_deletePerson_Then_returnTrue() {
            when(mockJsonFileDatabase.deletePerson("firstName",
                    "lastName"))
                    .thenReturn(true);
            assertTrue(personDAO.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deletePerson_Then_returnFalse() {
            when(mockJsonFileDatabase.deletePerson("firstName",
                    "lastName"))
                    .thenReturn(false);
            assertFalse(personDAO.delete(
                    "firstName",
                    "lastName"));
        }
    }
}