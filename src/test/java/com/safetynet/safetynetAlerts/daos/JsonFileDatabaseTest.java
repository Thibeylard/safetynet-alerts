package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
            @DisplayName("Success case - True")
            void Given_firestationParameters_When_addFirestation_Then_returnTrue() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();
                assertThat(jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()))
                        .isTrue();
            }

            @Test
            @DisplayName("Success case - Addition")
            void Given_firestationParameters_When_addFirestation_Then_createNewFirestation() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();

                jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(newFirestation);
            }

            @Test
            @DisplayName("Illegal override case")
            void Given_identicalExistantData_When_addFirestation_Then_throwsIllegalDataOverrideException() throws Exception {
                Firestation newFirestation = FirestationFactory.createFirestation();
                firestations.add(newFirestation); // firestation is already present in data

                assertThrows(IllegalDataOverrideException.class,
                        () -> jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()));
            }

            @Test
            @DisplayName("IO error case")
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
            @DisplayName("Success case - True")
            void Given_firestationParameters_When_updateFirestation_Then_returnTrue() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();

                firestations.add(firestationToUpdate);

                assertThat(jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1))
                        .isTrue();
            }

            @Test
            @DisplayName("Success case - Update")
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
            @DisplayName("Not found case")
            void Given_dataToUpdateNotPresent_When_updateFirestation_Then_throwsNoSuchDataException() throws Exception {
                Firestation firestationToUpdate = FirestationFactory.createFirestation();

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updateFirestation(firestationToUpdate.getAddress(), firestationToUpdate.getStation() + 1));
            }

            @Test
            @DisplayName("IO error case")
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
            @DisplayName("By address : Success case - True")
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_returnTrue() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();
                firestations.add(firestationToDelete);
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()))
                        .isTrue();
            }

            @Test
            @DisplayName("By number : Success case - True")
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_returnTrue() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();
                firestations.add(firestationToDelete);
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()))
                        .isTrue();
            }

            @Test
            @DisplayName("By address : Success case - Delete")
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_firestationDeleted() throws Exception {
                Firestation firestationToDelete = FirestationFactory
                        .createFirestation(Addresses.MARCONI.getName(), null);
                Firestation firestationSaved = FirestationFactory
                        .createFirestation(Addresses.CIRCLE.getName(), null);

                firestations.add(firestationToDelete);
                firestations.add(firestationSaved);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress());

                assertThat(firestations)
                        .isNotNull()
                        .doesNotContain(firestationToDelete)
                        .contains(firestationSaved);
            }

            @Test
            @DisplayName("By number : Success case - Delete")
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_noMoreFirestationWithNumber() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation(null, 4);
                Firestation firestationToKeep = FirestationFactory.createFirestation(null, 3);

                firestations.add(firestationToDelete);
                firestations.add(firestationToKeep);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .contains(firestationToKeep)
                        .doesNotContain(firestationToDelete);
            }

            @Test
            @DisplayName("Not found cases")
            void Given_dataToDeleteNotPresent_When_deleteFirestation_Then_throwsNoSuchDataException() throws Exception {
                Firestation firestationToDelete = FirestationFactory.createFirestation();

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()));
                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()));
            }

            @Test
            @DisplayName("IO error cases")
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

        //    ------------------------------------------------------------------------------ GET
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("get()")
        class getTestMethods {

            @Test
            @DisplayName("getFirestation() : Success case")
            void Given_matchingAddress_When_getFirestation_Then_returnFirestation() throws Exception {
                Firestation firestation = FirestationFactory.createFirestation();
                Firestation firestation2 = FirestationFactory.createFirestation();
                firestations.add(firestation);
                firestations.add(firestation2);

                assertThat(jsonFileDatabase.getFirestation(firestation.getAddress()))
                        .isNotNull()
                        .isEqualToComparingFieldByField(firestation);
            }

            @Test
            @DisplayName("getFirestation() : Not found case")
            void Given_notMatchingAddress_When_getFirestation_Then_throwNoSuchDataException() throws Exception {
                Firestation firestation = FirestationFactory.createFirestation();
                Firestation firestation2 = FirestationFactory.createFirestation();
                firestations.add(firestation);
                firestations.add(firestation2);

                assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getFirestation("unreferenced address"));
            }

            @Test
            @DisplayName("getFirestations() : Success case")
            void Given_matchingStationNumber_When_getFirestations_Then_returnFirestationList() throws Exception {
                Firestation firestation = FirestationFactory.createFirestation(null, 2);
                Firestation firestation2 = FirestationFactory.createFirestation(null, 2);
                Firestation firestation3 = FirestationFactory.createFirestation(null, 3);
                Firestation firestation4 = FirestationFactory.createFirestation(null, 4);
                firestations.addAll(List.of(firestation, firestation2, firestation3, firestation4));

                assertThat(jsonFileDatabase.getFirestations(2))
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(firestation, firestation2);
            }

            @Test
            @DisplayName("getFirestations() : Not found case")
            void Given_notMatchingStationNumber_When_getFirestations_Then_throwNoSuchDataException() throws Exception {
                Firestation firestation = FirestationFactory.createFirestation(null, 2);
                Firestation firestation2 = FirestationFactory.createFirestation(null, 2);
                Firestation firestation3 = FirestationFactory.createFirestation(null, 3);
                Firestation firestation4 = FirestationFactory.createFirestation(null, 4);
                firestations.addAll(List.of(firestation, firestation2, firestation3, firestation4));

                assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getFirestations(1));
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
            @DisplayName("Success case - True")
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(false);

                assertThat(jsonFileDatabase.addMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        newMedicalRecord.getBirthDate(),
                        newMedicalRecord.getMedications(),
                        newMedicalRecord.getAllergies()
                )).isTrue();
            }

            @Test
            @DisplayName("Success case - Addition")
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_createNewMedicalRecord() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
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
            @DisplayName("Illegal override case")
            void Given_identicalExistantData_When_addMedicalRecord_Then_throwsIllegalDataOverrideException() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
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
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_addMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
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
            @DisplayName("Success case - True")
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        false);

                medicalRecords.add(newMedicalRecord);

                assertThat(jsonFileDatabase.updateMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        "",
                        null,
                        List.of("peanuts", "wheat")
                )).isTrue();
            }

            @Test
            @DisplayName("Success case - Update")
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_updateOriginalMedicalRecord() throws Exception {
                MedicalRecord originalMedicalRecord = MedicalRecordFactory.createMedicalRecord(false);

                MedicalRecord updatedMedicalRecord = MedicalRecordFactory.createMedicalRecord(
                        originalMedicalRecord.getFirstName(),
                        originalMedicalRecord.getLastName(),
                        false);

                originalMedicalRecord.setAllergies(List.of(""));
                updatedMedicalRecord.setAllergies(List.of("peanuts", "wheat"));

                medicalRecords.add(originalMedicalRecord);

                jsonFileDatabase.updateMedicalRecord(
                        originalMedicalRecord.getFirstName(),
                        originalMedicalRecord.getLastName(),
                        updatedMedicalRecord.getBirthDate(),
                        updatedMedicalRecord.getMedications(),
                        updatedMedicalRecord.getAllergies()
                );

                assertThat(medicalRecords)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedMedicalRecord);
            }

            @Test
            @DisplayName("Not found case")
            void Given_dataToUpdateNotPresent_When_updateMedicalRecord_Then_throwsNoSuchDataException() throws Exception {
                MedicalRecord medicalRecordToUpdate = MedicalRecordFactory.createMedicalRecord(false);

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updateMedicalRecord(
                                medicalRecordToUpdate.getFirstName(),
                                medicalRecordToUpdate.getLastName(),
                                medicalRecordToUpdate.getBirthDate(),
                                medicalRecordToUpdate.getMedications(),
                                List.of("peanuts", "wheat")));
            }

            @Test
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_updateMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord medicalRecordToUpdate = MedicalRecordFactory.createMedicalRecord(
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
                                medicalRecordToUpdate.getBirthDate(),
                                medicalRecordToUpdate.getMedications(),
                                List.of("peanuts", "wheat")));
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            @DisplayName("Success case - True")
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_returnTrue() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(false);

                medicalRecords.add(medicalRecordToDelete);
                assertThat(jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName()))
                        .isTrue();
            }

            @Test
            @DisplayName("Success case - Deletion")
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_medicalRecordIsDeleted() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(false);

                medicalRecords.add(medicalRecordToDelete);

                jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName());

                assertThat(medicalRecords)
                        .isNotNull()
                        .doesNotContain(medicalRecordToDelete);
            }

            @Test
            @DisplayName("Not found case")
            void Given_dataToDeleteNotPresent_When_deleteMedicalRecord_Then_throwsNoSuchDataException() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(false);

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.deleteMedicalRecord(
                                medicalRecordToDelete.getFirstName(),
                                medicalRecordToDelete.getLastName()));
            }

            @Test
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_deleteMedicalRecord_Then_throwsIOException() throws Exception {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.createMedicalRecord(false);

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

        //    ------------------------------------------------------------------------------ GET
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("get()")
        class getTestMethods {

            @Test
            @DisplayName("Success case")
            void Given_matchingName_When_getMedicalRecord_Then_returnMedicalRecord() throws Exception {
                MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(false);
                MedicalRecord medicalRecord2 = MedicalRecordFactory.createMedicalRecord(false);
                MedicalRecord medicalRecord3 = MedicalRecordFactory.createMedicalRecord(true);

                medicalRecords.addAll(List.of(medicalRecord, medicalRecord2, medicalRecord3));

                assertThat(jsonFileDatabase.getMedicalRecord(medicalRecord.getFirstName(), medicalRecord.getLastName()))
                        .isNotNull()
                        .isEqualToComparingFieldByField(medicalRecord);

                assertThat(jsonFileDatabase.getMedicalRecord(medicalRecord3.getFirstName(), medicalRecord3.getLastName()))
                        .isNotNull()
                        .isEqualToComparingFieldByField(medicalRecord3);
            }

            @Test
            @DisplayName("Not found case")
            void Given_notMatchingName_When_getMedicalRecord_Then_throwNoSuchDataException() throws Exception {
                MedicalRecord medicalRecord = MedicalRecordFactory.createMedicalRecord(false);
                MedicalRecord medicalRecord2 = MedicalRecordFactory.createMedicalRecord(false);
                MedicalRecord medicalRecord3 = MedicalRecordFactory.createMedicalRecord(true);

                medicalRecords.addAll(List.of(medicalRecord, medicalRecord3));

                assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getMedicalRecord(medicalRecord2.getFirstName(), medicalRecord2.getLastName()));
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
            @DisplayName("Success case - True")
            void Given_personParameters_When_addPerson_Then_returnTrue() throws Exception {
                Person newPerson = PersonFactory.createPerson();


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
            @DisplayName("Success case - Addition")
            void Given_personParameters_When_addPerson_Then_createNewPerson() throws Exception {
                Person newPerson = PersonFactory.createPerson();

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
            @DisplayName("Illegal override case")
            void Given_identicalExistantData_When_addPerson_Then_throwsIllegalDataOverrideException() throws Exception {
                Person newPerson = PersonFactory.createPerson();

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
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_addPerson_Then_throwsIOException() throws Exception {
                Person newPerson = PersonFactory.createPerson();

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
            @DisplayName("Success case - True")
            void Given_personParameters_When_updatePerson_Then_returnTrue() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        null,
                        null,
                        Addresses.APPLEGATE,
                        null);

                Person updatedPerson = PersonFactory.createPerson(
                        null,
                        null,
                        Addresses.CIRCLE,
                        null);

                persons.add(originalPerson);

                assertThat(jsonFileDatabase.updatePerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        updatedPerson.getAddress(),
                        updatedPerson.getCity(),
                        updatedPerson.getZip(),
                        null,
                        null))
                        .isTrue();
            }

            @Test
            @DisplayName("Success case - Update")
            void Given_personParameters_When_updatePerson_Then_updateOriginalPerson() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        null,
                        null,
                        Addresses.APPLEGATE,
                        null);

                Person updatedPerson = PersonFactory.createPerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        Addresses.APPLEGATE,
                        null);

                updatedPerson.setPhone(originalPerson.getPhone());
                updatedPerson.setEmail("anotherMail@mail.com");

                persons.add(originalPerson);

                jsonFileDatabase.updatePerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        null,
                        null,
                        null,
                        updatedPerson.getPhone(),
                        updatedPerson.getEmail());

                assertThat(persons)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedPerson);
            }

            @Test
            @DisplayName("Not found case")
            void Given_dataToUpdateNotPresent_When_updatePerson_Then_throwsNoSuchDataException() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        null,
                        null,
                        Addresses.APPLEGATE,
                        null);

                assertThrows(NoSuchDataException.class,
                        () -> jsonFileDatabase.updatePerson(
                                originalPerson.getFirstName(),
                                originalPerson.getLastName(),
                                Addresses.CIRCLE.getName(),
                                Addresses.CIRCLE.getCity().getName(),
                                Addresses.CIRCLE.getCity().getZip(),
                                null,
                                null));
            }

            @Test
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_updatePerson_Then_throwsIOException() throws Exception {
                Person originalPerson = PersonFactory.createPerson(
                        null,
                        null,
                        Addresses.APPLEGATE,
                        null);

                persons.add(originalPerson);

                doThrow(new IOException())
                        .when(spyFactory)
                        .createGenerator(any(File.class), any(JsonEncoding.class));

                assertThrows(IOException.class,
                        () -> jsonFileDatabase.updatePerson(
                                originalPerson.getFirstName(),
                                originalPerson.getLastName(),
                                Addresses.CIRCLE.getName(),
                                Addresses.CIRCLE.getCity().getName(),
                                Addresses.CIRCLE.getCity().getZip(),
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
            @DisplayName("Success case - True")
            void Given_personParameters_When_deletePerson_Then_returnTrue() throws Exception {
                Person personToDelete = PersonFactory.createPerson();

                persons.add(personToDelete);
                assertThat(jsonFileDatabase.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName()))
                        .isTrue();
            }

            @Test
            @DisplayName("Success case - Deletion")
            void Given_personParameters_When_deletePerson_Then_personIsDeleted() throws Exception {
                Person personToDelete = PersonFactory.createPerson();

                persons.add(personToDelete);

                jsonFileDatabase.deletePerson(personToDelete.getFirstName(), personToDelete.getLastName());

                assertThat(persons)
                        .isNotNull()
                        .doesNotContain(personToDelete);
            }

            @Test
            @DisplayName("Not found case")
            void Given_dataToDeleteNotPresent_When_deletePerson_Then_throwsNoSuchDataException() throws Exception {
                Person personToDelete = PersonFactory.createPerson();

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
            @DisplayName("IO error case")
            void Given_IOExceptionOnWrite_When_deletePerson_Then_throwsIOException() throws Exception {
                Person personToDelete = PersonFactory.createPerson();

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

        //    ------------------------------------------------------------------------------ GET
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("get()")
        class getTestMethods {

            @Nested
            @DisplayName("getPerson()")
            class getPersonTestMethods {

                @Test
                @DisplayName("Success case")
                void Given_matchingName_When_getPerson_Then_returnPerson() throws Exception {
                    Person person = PersonFactory.createPerson();
                    Person person2 = PersonFactory.createPerson();
                    Person person3 = PersonFactory.createPerson();

                    persons.addAll(List.of(person, person2, person3));

                    assertThat(jsonFileDatabase.getPerson(person.getFirstName(), person.getLastName(), false))
                            .isNotNull()
                            .isEqualToComparingFieldByField(person);

                    assertThat(jsonFileDatabase.getPerson(person3.getFirstName(), person3.getLastName(), false))
                            .isNotNull()
                            .isEqualToComparingFieldByField(person3);
                }

                @Test
                @DisplayName("Not found case")
                void Given_notMatchingName_When_getPerson_Then_throwNoSuchDataException() throws Exception {
                    Person person = PersonFactory.createPerson();
                    Person person2 = PersonFactory.createPerson();
                    Person person3 = PersonFactory.createPerson();

                    persons.addAll(List.of(person, person3));

                    assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getPerson(person2.getFirstName(), person2.getLastName(), false));
                }

                @Test
                @DisplayName("With MedicalRecord : Success case")
                void Given_matchingNameWithMedicalRecord_When_getPerson_Then_returnPerson() throws Exception {
                    Person person = PersonFactory.createAdults(1, null, null).get(0);
                    Person person2 = PersonFactory.createPerson();
                    Person person3 = PersonFactory.createPerson();

                    persons.addAll(List.of(person, person3));
                    medicalRecords.add(person.getMedicalRecord()
                            .orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName())));

                    assertThat(jsonFileDatabase.getPerson(person.getFirstName(), person.getLastName(), true))
                            .isNotNull()
                            .isEqualToComparingFieldByField(person);
                }

                @Test
                @DisplayName("With MedicalRecord : No MedicalRecord error case")
                void Given_matchingNameButNoMedicalRecord_When_getPerson_Then_throwNoMedicalRecordException() throws Exception {
                    Person person = PersonFactory.createPerson();
                    Person person2 = PersonFactory.createPerson();
                    Person person3 = PersonFactory.createPerson();

                    persons.addAll(List.of(person, person3));

                    assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getPerson(person2.getFirstName(), person2.getLastName(), true));

                }
            }


            @Nested
            @DisplayName("getPersons(address)")
            class getPersonsByAddressTestMethods {
                @Test
                @DisplayName("Success case")
                void Given_matchingAddress_When_getPersons_Then_returnPersonList() throws Exception {
                    List<Person> persons1 = PersonFactory.createPersons(2, null, Addresses.APPLEGATE);
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE);

                    persons.addAll(persons1);
                    persons.addAll(persons2);

                    assertThat(jsonFileDatabase.getPersonFromAddress(Addresses.APPLEGATE.getName(), false))
                            .isNotNull()
                            .usingRecursiveFieldByFieldElementComparator()
                            .containsAll(persons1);
                }

                @Test
                @DisplayName("Not found case")
                void Given_notMatchingAddress_When_getPersons_Then_throwNoSuchDataException() throws Exception {
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE);
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE);

                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getPersonFromAddress(Addresses.MARCONI.getName(), false));

                }

                @Test
                @DisplayName("With MedicalRecord : Success case")
                void Given_matchingAddressWithMedicalRecord_When_getPersons_Then_returnPersonList() throws Exception {
                    List<Person> persons1 = PersonFactory.createAdults(2, null, Addresses.APPLEGATE);
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE);
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE);

                    persons.addAll(persons1);
                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    medicalRecords.add(persons1.get(0).getMedicalRecord()
                            .orElseThrow(() -> new NoMedicalRecordException(persons1.get(0).getFirstName(), persons1.get(0).getLastName())));
                    medicalRecords.add(persons1.get(1).getMedicalRecord()
                            .orElseThrow(() -> new NoMedicalRecordException(persons1.get(1).getFirstName(), persons1.get(1).getLastName())));

                    assertThat(jsonFileDatabase.getPersonFromAddress(Addresses.APPLEGATE.getName(), true))
                            .isNotNull()
                            .usingRecursiveFieldByFieldElementComparator()
                            .containsAll(persons1);
                }

                @Test
                @DisplayName("With MedicalRecord : No MedicalRecord error case")
                void Given_matchingAddressButNoMedicalRecord_When_getPersons_Then_throwNoMedicalRecordException() throws Exception {
                    List<Person> persons1 = PersonFactory.createPersons(2, null, Addresses.APPLEGATE);
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE);
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE);

                    persons.addAll(persons1);
                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    assertThrows(NoMedicalRecordException.class, () -> jsonFileDatabase.getPersonFromAddress(Addresses.APPLEGATE.getName(), true));
                }
            }


            @Nested
            @DisplayName("getPersons(city)")
            class getPersonsByCityTestMethods {
                @Test
                @DisplayName("Success case")
                void Given_matchingCity_When_getPersons_Then_returnPersonList() throws Exception {
                    List<Person> persons1 = PersonFactory.createPersons(2, null, Addresses.APPLEGATE); // OakPark City
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE); // OakPark City

                    persons.addAll(persons1);
                    persons.addAll(persons2);

                    assertThat(jsonFileDatabase.getPersonFromCity(Addresses.APPLEGATE.getCity().getName(), false))
                            .isNotNull()
                            .usingRecursiveFieldByFieldElementComparator()
                            .containsAll(persons);
                }

                @Test
                @DisplayName("Not found case")
                void Given_notMatchingCity_When_getPersons_Then_throwNoSuchDataException() throws Exception {
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.CIRCLE); // OakPark City
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE); // Waltham City

                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    assertThrows(NoSuchDataException.class, () -> jsonFileDatabase.getPersonFromCity(Addresses.MARCONI.getCity().getName(), false));

                }

                @Test
                @DisplayName("With MedicalRecord : Success case")
                void Given_matchingCityWithMedicalRecord_When_getPersons_Then_returnPersonList() throws Exception {
                    List<Person> persons1 = PersonFactory.createAdults(2, null, Addresses.APPLEGATE); // OakPark City
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.HERITAGE); // Waltham City
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE); // Waltham City

                    persons.addAll(persons1);
                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    medicalRecords.add(persons1.get(0).getMedicalRecord()
                            .orElseThrow(() -> new NoMedicalRecordException(persons1.get(0).getFirstName(), persons1.get(0).getLastName())));
                    medicalRecords.add(persons1.get(1).getMedicalRecord()
                            .orElseThrow(() -> new NoMedicalRecordException(persons1.get(1).getFirstName(), persons1.get(1).getLastName())));

                    assertThat(jsonFileDatabase.getPersonFromCity(Addresses.APPLEGATE.getCity().getName(), true))
                            .isNotNull()
                            .usingRecursiveFieldByFieldElementComparator()
                            .containsAll(persons1);
                }

                @Test
                @DisplayName("With MedicalRecord : No MedicalRecord error case")
                void Given_matchingCityButNoMedicalRecord_When_getPersons_Then_throwNoMedicalRecordException() throws Exception {
                    List<Person> persons1 = PersonFactory.createPersons(2, null, Addresses.APPLEGATE); // OakPark City
                    List<Person> persons2 = PersonFactory.createPersons(3, null, Addresses.HERITAGE); // Waltham City
                    List<Person> persons3 = PersonFactory.createPersons(1, null, Addresses.ELMDRIVE); // Waltham City

                    persons.addAll(persons1);
                    persons.addAll(persons2);
                    persons.addAll(persons3);

                    assertThrows(NoMedicalRecordException.class, () -> jsonFileDatabase.getPersonFromCity(Addresses.APPLEGATE.getCity().getName(), true));

                }
            }

        }
    }
}