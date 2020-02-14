package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("JsonFileDatabase Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonFileDatabaseTest {

    JsonFileDatabaseTest() {
    }

    @MockBean
    private ObjectMapper mockMapper;

    private JsonFileDatabase jsonFileDatabase;

    private List<Firestation> firestations = new ArrayList<Firestation>();
    private List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
    private List<Person> persons = new ArrayList<Person>();

    @BeforeAll
    public void resetDatafileContent() throws IOException {
        when(mockMapper.readTree(any(File.class))).thenReturn(JsonNodeFactory.instance.objectNode());
        when(mockMapper.convertValue(any(JsonNode.class), any(TypeReference.class))).thenReturn(
                new JsonFileDatabaseDTO(persons, firestations, medicalRecords));
        jsonFileDatabase = new JsonFileDatabase(mockMapper);
    }


    @BeforeEach
    public void resetData() {
        reset(mockMapper);
        firestations.clear();
        medicalRecords.clear();
        persons.clear();
    }

    @Nested
    @DisplayName("FirestationDAO :")
    class FirestationDAOMethods {
        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_firestationParameters_When_addFirestation_Then_returnTrue() {
                Firestation newFirestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                assertThat(jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_addFirestation_Then_createNewFirestation() {
                Firestation newFirestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(newFirestation);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_addFirestation_Then_returnFalse() throws IOException {
                Firestation newFirestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation()))
                        .isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_firestationParameters_When_updateFirestation_Then_returnTrue() {
                assertThat(jsonFileDatabase.updateFirestation("1509 Culver St", 5))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_updateFirestation_Then_firestationUpdated() {
                Firestation originalFirestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());
                Firestation updatedFirestation = new Firestation(originalFirestation.getAddress(), originalFirestation.getStation() + 1);

                firestations.add(originalFirestation);

                jsonFileDatabase.updateFirestation(originalFirestation.getAddress(), updatedFirestation.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedFirestation);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_updateFirestation_Then_returnFalse() throws IOException {
                Firestation newFirestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.updateFirestation(newFirestation.getAddress(), newFirestation.getStation() + 1))
                        .isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_returnTrue() {
                Firestation firestationToDelete = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());
                firestations.add(firestationToDelete);
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_returnTrue() {
                Firestation firestationToDelete = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_allFirestationsDeleted() {
                Firestation firestationToDelete1 = FirestationFactory
                        .getFirestation(Optional.empty(), Optional.empty());
                Firestation firestationToDelete2 = FirestationFactory
                        .getFirestation(Optional.of(firestationToDelete1.getAddress()), Optional.empty());

                firestations.add(firestationToDelete1);
                firestations.add(firestationToDelete2);

                jsonFileDatabase.deleteFirestation(firestationToDelete1.getAddress());

                assertThat(firestations)
                        .isNotNull()
                        .doesNotContain(firestationToDelete1)
                        .doesNotContain(firestationToDelete2);
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_noMoreFirestationWithNumber() {
                Firestation firestationToDelete = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());
                Firestation firestationToKeep = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                firestations.add(firestationToDelete);
                firestations.add(firestationToKeep);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getStation());

                assertThat(firestations)
                        .isNotNull()
                        .contains(firestationToKeep)
                        .doesNotContain(firestationToDelete);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_deleteFirestation_Then_returnFalse() throws IOException {
                Firestation firestationToDelete = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));

                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress()))
                        .isFalse();
                assertThat(jsonFileDatabase.deleteFirestation(firestationToDelete.getStation()))
                        .isFalse();
            }
        }
    }

    @Nested
    @DisplayName("MedicalRecordDAO :")
    class MedicalRecordDAOMethods {
        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_returnTrue() {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                assertThat(jsonFileDatabase.addMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        newMedicalRecord.getBirthDate(),
                        newMedicalRecord.getMedications(),
                        newMedicalRecord.getAllergies()
                )).isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_addMedicalRecord_Then_createNewMedicalRecord() {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

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
            void Given_validParametersButIOExceptionOccurs_When_addMedicalRecord_Then_returnFalse() throws IOException {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.addMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        newMedicalRecord.getBirthDate(),
                        newMedicalRecord.getMedications(),
                        newMedicalRecord.getAllergies()
                )).isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_returnTrue() {
                MedicalRecord newMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                assertThat(jsonFileDatabase.updateMedicalRecord(
                        newMedicalRecord.getFirstName(),
                        newMedicalRecord.getLastName(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(List.of("peanuts", "wheat"))
                )).isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_updateMedicalRecord_Then_updateOriginalMedicalRecord() {
                MedicalRecord originalMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                MedicalRecord updatedMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.of(originalMedicalRecord.getFirstName()),
                        Optional.of(originalMedicalRecord.getLastName()));

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
            void Given_validParametersButIOExceptionOccurs_When_updateMedicalRecord_Then_returnFalse() throws IOException {
                MedicalRecord originalMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());
                MedicalRecord updatedMedicalRecord = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                originalMedicalRecord.setAllergies(List.of(""));
                updatedMedicalRecord.setAllergies(List.of("peanuts", "wheat"));

                medicalRecords.add(originalMedicalRecord);

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.updateMedicalRecord(
                        originalMedicalRecord.getFirstName(),
                        originalMedicalRecord.getLastName(),
                        Optional.of(updatedMedicalRecord.getBirthDate()),
                        Optional.of(updatedMedicalRecord.getMedications()),
                        Optional.of(updatedMedicalRecord.getAllergies())
                )).isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_returnTrue() {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty());

                medicalRecords.add(medicalRecordToDelete);
                assertThat(jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName()))
                        .isTrue();
            }

            @Test
            void Given_medicalRecordParameters_When_deleteMedicalRecord_Then_medicalRecordIsDeleted() {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty()
                );

                medicalRecords.add(medicalRecordToDelete);

                jsonFileDatabase.deleteMedicalRecord(medicalRecordToDelete.getFirstName(), medicalRecordToDelete.getLastName());

                assertThat(medicalRecords)
                        .isNotNull()
                        .doesNotContain(medicalRecordToDelete);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_deleteMedicalRecord_Then_returnFalse() throws IOException {
                MedicalRecord medicalRecordToDelete = MedicalRecordFactory.getMedicalRecord(
                        false,
                        Optional.empty(),
                        Optional.empty()
                );

                medicalRecords.add(medicalRecordToDelete);

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));

                assertThat(jsonFileDatabase.deleteMedicalRecord(
                        medicalRecordToDelete.getFirstName(),
                        medicalRecordToDelete.getLastName()))
                        .isFalse();
            }
        }
    }

    @Nested
    @DisplayName("PersonDAO :")
    class PersonDAOMethods {
        //    --------------------------------------------------------------------------------- ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_personParameters_When_addPerson_Then_returnTrue() {
                Person newPerson = PersonFactory.getPerson(
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
            void Given_personParameters_When_addPerson_Then_createNewPerson() {
                Person newPerson = PersonFactory.getPerson(
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
            void Given_validParametersButIOExceptionOccurs_When_addPerson_Then_returnFalse() throws IOException {
                Person newPerson = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.addPerson(
                        newPerson.getFirstName(),
                        newPerson.getLastName(),
                        newPerson.getAddress(),
                        newPerson.getCity(),
                        newPerson.getZip(),
                        newPerson.getPhone(),
                        newPerson.getEmail()
                )).isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_personParameters_When_updatePerson_Then_returnTrue() {
                Person originalPerson = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                Person updatedPerson = PersonFactory.getPerson(
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
            void Given_personParameters_When_updatePerson_Then_updateOriginalPerson() {
                Person originalPerson = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                Person updatedPerson = PersonFactory.getPerson(
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
            void Given_validParametersButIOExceptionOccurs_When_updatePerson_Then_returnFalse() throws IOException {
                Person originalPerson = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.APPLEGATE),
                        Optional.empty()
                );

                Person updatedPerson = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(Addresses.CIRCLE),
                        Optional.empty()
                );


                persons.add(originalPerson);

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.updatePerson(
                        originalPerson.getFirstName(),
                        originalPerson.getLastName(),
                        Optional.of(updatedPerson.getAddress()),
                        Optional.of(updatedPerson.getCity()),
                        Optional.of(updatedPerson.getZip()),
                        Optional.empty(),
                        Optional.empty()
                )).isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_personParameters_When_deletePerson_Then_returnTrue() {
                Person personToDelete = PersonFactory.getPerson(
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
            void Given_personParameters_When_deletePerson_Then_personIsDeleted() {
                Person personToDelete = PersonFactory.getPerson(
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
            void Given_validParametersButIOExceptionOccurs_When_deletePerson_Then_returnFalse() throws IOException {
                Person personToDelete = PersonFactory.getPerson(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty(),
                        Optional.empty()
                );

                persons.add(personToDelete);

                doThrow(IOException.class).when(mockMapper).writeValue(any(File.class), any(JsonFileDatabaseDTO.class));

                assertThat(jsonFileDatabase.deletePerson(
                        personToDelete.getFirstName(),
                        personToDelete.getLastName()))
                        .isFalse();
            }
        }
    }
}