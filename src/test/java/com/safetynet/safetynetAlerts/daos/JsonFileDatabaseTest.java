package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO check tests and add new ones
@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("JsonFileDatabase Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonFileDatabaseTest {

    @MockBean
    private ObjectMapper mapper;
    @SpyBean
    private JsonFactory spyFactory;
    @MockBean
    private JsonParser mockParser;
    @MockBean
    private JsonGenerator mockGenerator;
    @Value("${jsondatabase.src}")
    private String databaseSrc;

    JsonFileDatabaseTest() {
    }

    private JsonFileDatabase jsonFileDatabase;

    private List<Firestation> firestations = new ArrayList<Firestation>();
    private List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
    private List<Person> persons = new ArrayList<Person>();

    @BeforeAll
    public void initializeMocks() throws IOException {
        doReturn(mockParser).when(spyFactory).createParser(any(File.class));
        when(mockParser.readValueAs(JsonFileDatabaseDTO.class)).thenReturn(new JsonFileDatabaseDTO(persons, firestations, medicalRecords));
        jsonFileDatabase = new JsonFileDatabase(spyFactory, mapper, this.databaseSrc);
    }


    @BeforeEach
    public void resetData() throws IOException {
        doReturn(mockGenerator).when(spyFactory).createGenerator(any(File.class), any(JsonEncoding.class));
        firestations.clear();
        medicalRecords.clear();
        persons.clear();
    }

    // ==================================================================================================== FirestationDAO TESTS
    // =========================================================================================================================
    @Nested
    @DisplayName("FirestationDAO related:")
    class FirestationDAOMethods {

        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_firestationParameters_When_addFirestation_Then_returnTrue() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();
                assertThat(jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_addFirestation_Then_createNewFirestation() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();

                jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(newFirestation);
            }

            @Test
            void Given_identicalExistantData_When_addFirestation_Then_throwsIllegalDataOverrideException() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();
                firestations.add(newFirestation); // firestation is already present in data

                assertThrows(IllegalDataOverrideException.class,
                        () -> jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_addFirestation_Then_throwsIOException() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));
                assertThrows(IOException.class,
                        () -> jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()));
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_firestationParameters_When_updateFirestation_Then_returnTrue() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();

                firestations.add(firestationToUpdate);

                assertThat(jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_updateFirestation_Then_firestationUpdated() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();
                Firestation updatedFirestation = new Firestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1);

                firestations.add(firestationToUpdate);

                jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), updatedFirestation.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedFirestation);
            }

            @Test
            void Given_dataToUpdateNotPresent_When_updateFirestation_Then_throwsNoSuchDataException() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1));
            }

            @Test
            void Given_IOExceptionOnWrite_When_updateFirestation_Then_throwsIOException() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();

                firestations.add(firestationToUpdate);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));
                assertThrows(IOException.class,
                        () -> jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1));
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_returnTrue() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();
                firestations.add(firestationToDelete);
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_returnTrue() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();
                firestations.add(firestationToDelete);
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_allFirestationsDeleted() throws Exception {
                Firestation firestationToDelete1 = FirestationFactory
                        .createFirestation();
                Firestation firestationToDelete2 = FirestationFactory
                        .createFirestation(Optional.of(firestationToDelete1.getAddress()), Optional.empty());

                firestations.add(firestationToDelete1);
                firestations.add(firestationToDelete2);

                jsonFileDatabase.deleteFirestation(firestationToDelete1.getAddress());

                assertThat(firestations)
                        .isNotNull()
                        .doesNotContain(firestationToDelete1)
                        .doesNotContain(firestationToDelete2);
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_noMoreFirestationWithNumber() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation(Optional.empty(), Optional.of(4));
                Firestation firestationToKeep = FirestationFactory.createFirestation(Optional.empty(), Optional.of(3));

                firestations.add(firestationToDelete);
                firestations.add(firestationToKeep);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .contains(firestationToKeep)
                        .doesNotContain(firestationToDelete);
            }

            @Test
            void Given_dataToDeleteNotPresent_When_deleteFirestation_Then_throwsNoSuchDataException() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()));
                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_deleteFirestation_Then_throwsIOException() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                firestations.add(firestationToDelete);
                assertThrows(IOException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()));

                firestations.add(firestationToDelete);
                assertThrows(IOException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()));
            }
        }
    }

    // ================================================================================================== MedicalRecordDAO TESTS
    // =========================================================================================================================
    @Nested
    @DisplayName("MedicalRecordDAO related :")
    class MedicalRecordDAOMethods {
        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                assertThat(jsonFileDatabase.addMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        newMedicalRecord.getBirthDate(),
                        newMedicalRecord.getMedications(),
                        newMedicalRecord.getAllergies()
                )).isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_createNewMedicalRecord() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                jsonFileDatabase.addMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        newMedicalRecord.getBirthDate(),
                        newMedicalRecord.getMedications(),
                        newMedicalRecord.getAllergies());

                assertThat(medicalRecords)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(newMedicalRecord);
            }

            @Test
            void Given_identicalExistantData_When_addMedicalRecord_Then_throwsIllegalDataOverrideException() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                medicalRecords.add(newMedicalRecord);


                assertThrows(IllegalDataOverrideException.class,
                        () -> jsonFileDatabase.addMedicalRecord(
                                newMedicalRecord.getFirstName(),
                                newMedicalRecord.getLastName(),
                                newMedicalRecord.getBirthDate(),
                                newMedicalRecord.getMedications(),
                                newMedicalRecord.getAllergies()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_addMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.addMedicalRecord(
                                newMedicalRecord.getFirstName(),
                                newMedicalRecord.getLastName(),
                                newMedicalRecord.getBirthDate(),
                                newMedicalRecord.getMedications(),
                                newMedicalRecord.getAllergies()));
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                medicalRecords.add(newMedicalRecord);

                assertThat(jsonFileDatabase.updateMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(List.of("peanuts", "wheat"))
                )).isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_updateOriginalMedicalRecord() throws Exception {
                MedicalRecord originalMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                MedicalRecord updatedMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        Optional.of(originalMedicalRecord.getFirstName()),
                        Optional.of(originalMedicalRecord.getLastName()),
                        false);

                originalMedicalRecord.setAllergies(List.of(""));
                updatedMedicalRecord.setAllergies(List.of("peanuts", "wheat"));

                medicalRecords.add(originalMedicalRecord);

                jsonFileDatabase.updateMedicalRecord(
                        originalMedicalRecord.getFirstName(),
                        originalMedicalRecord.getLastName(),
                        Optional.of(updatedMedicalRecord.getBirthDate()),
                        Optional.of(updatedMedicalRecord.getMedications()),
                        Optional.of(updatedMedicalRecord.getAllergies())
                );

                assertThat(medicalRecords)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedMedicalRecord);
            }

            @Test
            void Given_dataToUpdateNotPresent_When_updateMedicalRecord_Then_throwsNoSuchDataException() throws Exception {
                MedicalRecord medicalRecordToUpdate = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updateMedicalRecord(
                                medicalRecordToUpdate.getFirstName(),
                                medicalRecordToUpdate.getLastName(),
                                Optional.of(medicalRecordToUpdate.getBirthDate()),
                                Optional.of(medicalRecordToUpdate.getMedications()),
                                Optional.of(List.of("peanuts", "wheat"))));
            }

            @Test
            void Given_IOExceptionOnWrite_When_updateMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord medicalRecordToUpdate = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                medicalRecordToUpdate.setAllergies(List.of(""));

                medicalRecords.add(medicalRecordToUpdate);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.updateMedicalRecord(
                                medicalRecordToUpdate.getFirstName(),
                                medicalRecordToUpdate.getLastName(),
                                Optional.of(medicalRecordToUpdate.getBirthDate()),
                                Optional.of(medicalRecordToUpdate.getMedications()),
                                Optional.of(List.of("peanuts", "wheat"))));
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                medicalRecords.add(medicalRecordToDelete);
                assertThat(jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName()))
                        .isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_medicalRecordIsDeleted() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false
                );

                medicalRecords.add(medicalRecordToDelete);

                jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName());

                assertThat(medicalRecords)
                        .isNotNull()
                        .doesNotContain(medicalRecordToDelete);
            }

            @Test
            void Given_dataToDeleteNotPresent_When_deleteMedicalRecord_Then_throwsNoSuchDataException() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false
                );

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteMedicalRecord(
                                medicalRecordToDelete.getFirstName(),
                                medicalRecordToDelete.getLastName()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_deleteMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(
                        Optional.empty(),
                        Optional.empty(),
                        false);

                medicalRecords.add(medicalRecordToDelete);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.deleteMedicalRecord(
                                medicalRecordToDelete.getFirstName(),
                                medicalRecordToDelete.getLastName()));
            }
        }
    }

    // ========================================================================================================= PersonDAO TESTS
    // =========================================================================================================================
    @Nested
    @DisplayName("PersonDAO :")
    class PersonDAOMethods {
        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_personParameters_When_addPerson_Then_returnTrue() throws Exception {
                Person newPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );


                assertThat(jsonFileDatabase.addPerson(
                        newPerson.getFirstName(),
                        newPerson.getLastName(),
                        newPerson.getAddress(),
                        newPerson.getCity(),
                        newPerson.getZip(),
                        newPerson.getPhone(),
                        newPerson.getEmail()
                )).isTrue();
            }

            @Test
            void Given_personParameters_When_addPerson_Then_createNewPerson() throws Exception {
                Person newPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                jsonFileDatabase.addPerson(
                        newPerson.getFirstName(),
                        newPerson.getLastName(),
                        newPerson.getAddress(),
                        newPerson.getCity(),
                        newPerson.getZip(),
                        newPerson.getPhone(),
                        newPerson.getEmail()
                );

                assertThat(persons)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(newPerson);
            }

            @Test
            void Given_identicalExistantData_When_addPerson_Then_throwsIllegalDataOverrideException() throws Exception {
                Person newPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(newPerson);

                assertThrows(IllegalDataOverrideException.class,
                        () -> jsonFileDatabase.addPerson(
                                newPerson.getFirstName(),
                                newPerson.getLastName(),
                                newPerson.getAddress(),
                                newPerson.getCity(),
                                newPerson.getZip(),
                                newPerson.getPhone(),
                                newPerson.getEmail()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_addPerson_Then_throwsIOException() throws Exception {
                Person newPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.addPerson(
                                newPerson.getFirstName(),
                                newPerson.getLastName(),
                                newPerson.getAddress(),
                                newPerson.getCity(),
                                newPerson.getZip(),
                                newPerson.getPhone(),
                                newPerson.getEmail()));
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_personParameters_When_updatePerson_Then_returnTrue() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                Person updatedPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.CIRCLE),
                        Optional.empty()
                );

                persons.add(originalPerson);

                assertThat(jsonFileDatabase.updatePerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        Optional.of(updatedPerson.getAddress()),
                        Optional.of(updatedPerson.getCity()),
                        Optional.of(updatedPerson.getZip()),
                        Optional.empty(),
                        Optional.empty()
                )).isTrue();
            }

            @Test
            void Given_personParameters_When_updatePerson_Then_updateOriginalPerson() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                Person updatedPerson = PersonFactory.createPerson(
                        Optional.of(originalPerson.getFirstName()),
                        Optional.of(originalPerson.getLastName()),
                        Optional.of(Addresses.CIRCLE),
                        originalPerson.getMedicalRecord()
                );

                updatedPerson.setPhone(originalPerson.getPhone()); // Must access phone attribute by setter to make it equivalent.

                persons.add(originalPerson);

                jsonFileDatabase.updatePerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        Optional.of(updatedPerson.getAddress()),
                        Optional.of(updatedPerson.getCity()),
                        Optional.of(updatedPerson.getZip()),
                        Optional.empty(),
                        Optional.empty());

                assertThat(persons)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedPerson);
            }

            @Test
            void Given_dataToUpdateNotPresent_When_updatePerson_Then_throwsNoSuchDataException() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updatePerson(
                                originalPerson.getFirstName(),
                                originalPerson.getLastName(),
                                Optional.of(Addresses.CIRCLE.getName()),
                                Optional.of(Addresses.CIRCLE.getCity().getName()),
                                Optional.of(Addresses.CIRCLE.getCity().getZip()),
                                Optional.empty(),
                                Optional.empty()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_updatePerson_Then_throwsIOException() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                persons.add(originalPerson);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.updatePerson(
                                originalPerson.getFirstName(),
                                originalPerson.getLastName(),
                                Optional.of(Addresses.CIRCLE.getName()),
                                Optional.of(Addresses.CIRCLE.getCity().getName()),
                                Optional.of(Addresses.CIRCLE.getCity().getZip()),
                                Optional.empty(),
                                Optional.empty()));
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_personParameters_When_deletePerson_Then_returnTrue() throws Exception {
                Person personToDelete = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(personToDelete);
                assertThat(jsonFileDatabase.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName()))
                        .isTrue();
            }

            @Test
            void Given_personParameters_When_deletePerson_Then_personIsDeleted() throws Exception {
                Person personToDelete = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(personToDelete);

                jsonFileDatabase.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName());

                assertThat(persons)
                        .isNotNull()
                        .doesNotContain(personToDelete);
            }

            @Test
            void Given_dataToDeleteNotPresent_When_deletePerson_Then_throwsNoSuchDataException() throws Exception {
                Person personToDelete = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(personToDelete);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.deletePerson(
                                personToDelete.getFirstName(),
                                personToDelete.getLastName()));
            }

            @Test
            void Given_IOExceptionOnWrite_When_deletePerson_Then_throwsIOException() throws Exception {
                Person personToDelete = PersonFactory.createPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(personToDelete);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.deletePerson(
                                personToDelete.getFirstName(),
                                personToDelete.getLastName()));
            }
        }
    }
}