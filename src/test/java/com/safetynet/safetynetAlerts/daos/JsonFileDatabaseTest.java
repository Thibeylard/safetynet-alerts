package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("JsonFileDatabase Tests on :")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JsonFileDatabaseTest {

    @SpyBean
    private ObjectMapper spiedMapper;

    @Autowired
    private JsonFileDatabase jsonFileDatabase;

    private File testDataFile = new File("src/test/resources/testData.json");

    private JsonFileDatabaseDTO originalJsonFile;

    private List<Firestation> firestations = new ArrayList<Firestation>();
    private List<MedicalRecord> medicalRecords = new ArrayList<MedicalRecord>();
    private List<Person> persons = new ArrayList<Person>();

    @BeforeAll
    public void resetDatafileContent() throws IOException {
        jsonFileDatabase.setDataFile(testDataFile);
        originalJsonFile = spiedMapper.convertValue(spiedMapper.readTree(testDataFile), new TypeReference<JsonFileDatabaseDTO>() {});
    }

    @AfterEach
    public void resetData() throws IOException {
        reset(spiedMapper);
        spiedMapper.writeValue(testDataFile, originalJsonFile);
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
            void Given_firestationParameters_When_addFirestation_Then_returnTrue() throws IOException {
                assertThat(jsonFileDatabase.addFirestation("address", 5))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_addFirestation_Then_createNewFirestation() throws IOException {
                Firestation newFirestation = new Firestation("uniqueAddress", 5);
                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .doesNotContain(newFirestation);

                jsonFileDatabase.addFirestation(newFirestation.getAddress(), newFirestation.getStation());

                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                    .isNotNull()
                    .usingRecursiveFieldByFieldElementComparator()
                    .contains(newFirestation);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_addFirestation_Then_returnFalse() throws IOException {
                Firestation newFirestation = new Firestation("uniqueAddress", 5);

                doThrow(IOException.class).when(spiedMapper).writeValue(any(File.class),any(JsonFileDatabaseDTO.class));
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
            void Given_firestationParameters_When_updateFirestation_Then_returnTrue() throws IOException {
                assertThat(jsonFileDatabase.updateFirestation("1509 Culver St", 5))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_updateFirestation_Then_firestationUpdated() throws IOException {
                Firestation originalFirestation = new Firestation("1509 Culver St", 3);
                Firestation updatedFirestation = new Firestation(originalFirestation.getAddress(), 5);

                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(originalFirestation);

                jsonFileDatabase.updateFirestation(originalFirestation.getAddress(), 5);

                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(updatedFirestation);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_updateFirestation_Then_returnFalse() throws IOException {
                doThrow(IOException.class).when(spiedMapper).writeValue(any(File.class),any(JsonFileDatabaseDTO.class));
                assertThat(jsonFileDatabase.updateFirestation("1509 Culver St", 5))
                        .isFalse();
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_firestationParameters_When_deleteFirestation_Then_returnTrue() throws IOException {
                assertThat(jsonFileDatabase.deleteFirestation("1509 Culver St"))
                        .isTrue();
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_firestationDeleted() throws IOException {
                Firestation firestationToDelete = new Firestation("1509 Culver St", 3);
                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .contains(firestationToDelete);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getAddress());

                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .usingRecursiveFieldByFieldElementComparator()
                        .doesNotContain(firestationToDelete);
            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_noMoreFirestationWithNumber() throws IOException {
                Firestation firestationToDelete = new Firestation("1509 Culver St", 3);
                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .extracting(Firestation::getStation)
                        .contains(3);

                jsonFileDatabase.deleteFirestation(firestationToDelete.getStation());

                firestations = spiedMapper.convertValue(
                        spiedMapper.readTree(testDataFile).at("/firestations"),
                        new TypeReference<List<Firestation>>() {});

                assertThat(firestations)
                        .isNotNull()
                        .extracting(Firestation::getStation)
                        .doesNotContain(3);
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_deleteFirestation_Then_returnFalse() throws IOException {
                doThrow(IOException.class).when(spiedMapper).writeValue(any(File.class),any(JsonFileDatabaseDTO.class));

                assertThat(jsonFileDatabase.deleteFirestation("1509 Culver St"))
                        .isFalse();
                assertThat(jsonFileDatabase.deleteFirestation(3))
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
            void Given_medicalRecordsParameters_When_addMedicalRecord_Then_returnTrue() throws IOException {

            }

            @Test
            void Given_firestationParameters_When_addFirestation_Then_createNewFirestation() throws IOException {

            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_addFirestation_Then_returnFalse() throws IOException {

            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_firestationParameters_When_updateFirestation_Then_returnTrue() throws IOException {

            }

            @Test
            void Given_firestationParameters_When_updateFirestation_Then_firestationUpdated() throws IOException {

            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_updateFirestation_Then_returnFalse() throws IOException {

            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {

            @Test
            void Given_firestationParameters_When_deleteFirestation_Then_returnTrue() throws IOException {

            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByAddress_Then_firestationDeleted() throws IOException {

            }

            @Test
            void Given_firestationParameters_When_deleteFirestationByNumber_Then_noMoreFirestationWithNumber() throws IOException {

            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_deleteFirestation_Then_returnFalse() throws IOException {

            }
        }
    }

    @Nested
    @DisplayName("PersonDAO :")
    class PersonDAOMethods {

        //    ------------------------------------------------------------------------------ ADD
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("add()")
        class addTestMethods {
            @Test
            void Given_validParameters_When_addPerson_Then_returnTrue() {
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_addPerson_Then_returnFalse() {
            }
        }

        //    ------------------------------------------------------------------------------ UPDATE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("update()")
        class updateTestMethods {
            @Test
            void Given_validParameters_When_updatePerson_Then_returnTrue() {
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_updatePerson_Then_returnFalse() {
            }
        }

        //    ------------------------------------------------------------------------------ DELETE
        //    -------------------------------------------------------------------------------------
        @Nested
        @DisplayName("delete()")
        class deleteTestMethods {
            @Test
            void Given_validParameters_When_deletePerson_Then_returnTrue() {
            }

            @Test
            void Given_validParametersButIOExceptionOccurs_When_deletePerson_Then_returnFalse() {
            }
        }
    }
}