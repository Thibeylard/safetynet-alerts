package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.PersonDAO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
@DisplayName("PersonService Tests on :")
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
        void Given_validParameters_When_addPerson_Then_returnTrue() {
            when(mockPersonDAO.add("firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"))
                    .thenReturn(true);
            assertTrue(personService.add(
                    "firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_addPerson_Then_returnFalse() {
            when(mockPersonDAO.add("firstName",
                    "lastName",
                    "address",
                    "city", "zip",
                    "phone",
                    "email"))
                    .thenReturn(false);
            assertFalse(personService.add(
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
            when(mockPersonDAO.update("firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()))
                    .thenReturn(true);
            assertTrue(personService.update(
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
            when(mockPersonDAO.update("firstName",
                    "lastName",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.ofNullable("phone"),
                    Optional.empty()))
                    .thenReturn(false);
            assertFalse(personService.update(
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
            when(mockPersonDAO.delete("firstName",
                    "lastName"))
                    .thenReturn(true);
            assertTrue(personService.delete(
                    "firstName",
                    "lastName"));
        }

        @Test
        void Given_validParametersButDAOErrorOccurs_When_deletePerson_Then_returnFalse() {
            when(mockPersonDAO.delete("firstName",
                    "lastName"))
                    .thenReturn(false);
            assertFalse(personService.delete(
                    "firstName",
                    "lastName"));
        }
    }
}
    