package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
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
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertTrue(personDAO.update(
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
            doThrow(new NoSuchDataException()).when(mockJsonFileDatabase)
                    .updatePerson(anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertThrows(NoSuchDataException.class,
                    () -> personDAO.update(
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
            doThrow(new IOException()).when(mockJsonFileDatabase)
                    .updatePerson(anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString(),
                            anyString());
            assertThrows(IOException.class,
                    () -> personDAO.update(
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

    //    ------------------------------------------------------------------------------ GET
    //    -------------------------------------------------------------------------------------

    @Nested
    @DisplayName("get...() ")
    class getTestMethods {
        @Nested
        @DisplayName("getFromName() ")
        class getFromNameTestMethods {
            @Test
            void Given_validParameters_When_getFromName_Then_returnPerson() throws IOException, NoSuchDataException, NoMedicalRecordException {
                // Case with MedicalRecord not asked
                Person person = PersonFactory.createPerson();
                doReturn(person)
                        .when(mockJsonFileDatabase)
                        .getPerson(person.getFirstName(), person.getLastName(), false);

                assertThat(personDAO.getFromName(person.getFirstName(), person.getLastName(), false))
                        .isNotNull()
                        .usingRecursiveComparison()
                        .isEqualTo(person);

                // Case with MedicalRecord asked
                MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(null, null, false);
                Person personWithMedicalRecord = PersonFactory.createPerson(null, null, null, medicalRecord);

                doReturn(personWithMedicalRecord).when(mockJsonFileDatabase)
                        .getPerson(personWithMedicalRecord.getFirstName(), personWithMedicalRecord.getLastName(), true);

                assertThat(personDAO.getFromName(personWithMedicalRecord.getFirstName(), personWithMedicalRecord.getLastName(), true))
                        .isNotNull()
                        .usingRecursiveComparison()
                        .isEqualTo(personWithMedicalRecord);
            }

            @Test
            void Given_NoMedicalRecordException_When_getFromName_Then_throwsNoMedicalRecordException() throws NoSuchDataException, NoMedicalRecordException {
                Person person = PersonFactory.createPerson();

                doThrow(new NoMedicalRecordException(person.getFirstName(), person.getLastName()))
                        .when(mockJsonFileDatabase)
                        .getPerson(person.getFirstName(), person.getLastName(), true);

                assertThrows(NoMedicalRecordException.class,
                        () -> personDAO.getFromName(person.getFirstName(), person.getLastName(), true));
            }

            @Test
            void Given_NoSuchDataException_When_getFromName_Then_throwsNoSuchDataException() throws NoSuchDataException, NoMedicalRecordException {
                Person person = PersonFactory.createPerson();

                doThrow(new NoSuchDataException())
                        .when(mockJsonFileDatabase)
                        .getPerson(person.getFirstName(), person.getLastName(), true);

                assertThrows(NoSuchDataException.class,
                        () -> personDAO.getFromName(person.getFirstName(), person.getLastName(), true));
            }

        }

        @Nested
        @DisplayName("getFromAddress(String) ")
        class getFromAddressTestMethods {
            @Test
            void Given_validParameters_When_getFromAddress_Then_returnPersonList() throws IOException, NoSuchDataException, NoMedicalRecordException {
                // Case with MedicalRecord not asked
                List<Person> persons = PersonFactory.createPersons(3, null, Addresses.THOMASROAD);
                doReturn(persons)
                        .when(mockJsonFileDatabase)
                        .getPersonFromAddress(Addresses.THOMASROAD.getName(), false);

                assertThat(personDAO.getFromAddress(Addresses.THOMASROAD.getName(), false))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(persons);

                // Case with MedicalRecord asked
                List<Person> adults = PersonFactory.createAdults(4, null, Addresses.ELMDRIVE);

                doReturn(adults).when(mockJsonFileDatabase)
                        .getPersonFromAddress(Addresses.ELMDRIVE.getName(), true);

                assertThat(personDAO.getFromAddress(Addresses.ELMDRIVE.getName(), true))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(adults);
            }

            @Test
            void Given_NoMedicalRecordException_When_getFromAddress_Then_throwsNoMedicalRecordException() throws NoSuchDataException, NoMedicalRecordException {
                Person person = PersonFactory.createPerson();

                doThrow(new NoMedicalRecordException(person.getFirstName(), person.getLastName()))
                        .when(mockJsonFileDatabase)
                        .getPersonFromAddress(Addresses.ELMDRIVE.getName(), true);

                assertThrows(NoMedicalRecordException.class,
                        () -> personDAO.getFromAddress(Addresses.ELMDRIVE.getName(), true));
            }

            @Test
            void Given_NoSuchDataException_When_getFromAddress_Then_throwsNoSuchDataException() throws NoSuchDataException, NoMedicalRecordException {
                doThrow(new NoSuchDataException())
                        .when(mockJsonFileDatabase)
                        .getPersonFromAddress(Addresses.ELMDRIVE.getName(), true);

                assertThrows(NoSuchDataException.class,
                        () -> personDAO.getFromAddress(Addresses.ELMDRIVE.getName(), true));
            }

        }

        @Nested
        @DisplayName("getCommunity() ")
        class getCommunityTestMethods {
            @Test
            void Given_validParameters_When_getCommunity_Then_returnPersonList() throws IOException, NoSuchDataException, NoMedicalRecordException {
                // Case with MedicalRecord not asked
                List<Person> persons = PersonFactory.createPersons(3, null, Addresses.MARCONI);
                doReturn(persons)
                        .when(mockJsonFileDatabase)
                        .getPersonFromCity(Addresses.MARCONI.getCity().getName(), false);

                assertThat(personDAO.getCommunity(Addresses.MARCONI.getCity().getName(), false))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(persons);

                // Case with MedicalRecord asked
                List<Person> adults = PersonFactory.createAdults(4, null, Addresses.CIRCLE);

                doReturn(adults).when(mockJsonFileDatabase)
                        .getPersonFromCity(Addresses.CIRCLE.getCity().getName(), true);

                assertThat(personDAO.getCommunity(Addresses.CIRCLE.getCity().getName(), true))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .isEqualTo(adults);
            }

            @Test
            void Given_NoMedicalRecordException_When_getCommunity_Then_throwsNoMedicalRecordException() throws NoSuchDataException, NoMedicalRecordException {
                Person person = PersonFactory.createPerson();

                doThrow(new NoMedicalRecordException(person.getFirstName(), person.getLastName()))
                        .when(mockJsonFileDatabase)
                        .getPersonFromCity(Addresses.MARCONI.getName(), true);

                assertThrows(NoMedicalRecordException.class,
                        () -> personDAO.getCommunity(Addresses.MARCONI.getName(), true));
            }

            @Test
            void Given_NoSuchDataException_When_getCommunity_Then_throwsNoSuchDataException() throws NoSuchDataException, NoMedicalRecordException {
                doThrow(new NoSuchDataException())
                        .when(mockJsonFileDatabase)
                        .getPersonFromCity(Addresses.MARCONI.getName(), true);

                assertThrows(NoSuchDataException.class,
                        () -> personDAO.getCommunity(Addresses.MARCONI.getName(), true));
            }

        }

    }
}