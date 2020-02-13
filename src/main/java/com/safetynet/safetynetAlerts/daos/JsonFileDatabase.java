package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JsonFileDatabase {

    private final ObjectMapper mapper;
    private SafetynetAlertsData safetynetAlertsData = null;

    /* ---------------- SafetynetAlerts Datafile Model ---------------- */
    @JsonPropertyOrder({"persons", "firestations", "medicalrecords"})
    private static class SafetynetAlertsData {
        private List<Person> persons;
        private List<Firestation> firestations;
        private List<MedicalRecord> medicalRecords;

        public SafetynetAlertsData(@JsonProperty("persons") List<Person> pPersons,
                                   @JsonProperty("firestations") List<Firestation> pFirestations,
                                   @JsonProperty("medicalrecords") List<MedicalRecord> pMedicalRecords) {
            this.persons = pPersons;
            this.firestations = pFirestations;
            this.medicalRecords = pMedicalRecords;
        }
    }

    /**
     * @param pMapper Autoriwed ObjectMapper Singleton
     * @throws IOException Handles dataSrc file related errors
     */
    @Autowired
    public JsonFileDatabase(final ObjectMapper pMapper) throws IOException {
        this.mapper = pMapper;
        String dataSrc = "src/main/resources/static/data.json";
        this.safetynetAlertsData = mapper.convertValue(mapper.readTree(new File(dataSrc)), new TypeReference<SafetynetAlertsData>() {
        });
    }


    //    ---------------------------------------------------------------------------------------- FIRESTATION

    public boolean addFirestation(final String address, final int number) {
        return false;
    }

    public boolean updateFirestation(final String address, final int number) {
        return false;
    }

    public boolean deleteFirestation(final int number) {
        return false;
    }

    public boolean deleteFirestation(final String address) {
        return false;
    }

    //    ---------------------------------------------------------------------------------------- MEDICALRECORD

    public boolean addMedicalRecord(final String firstName,
                                    final String lastName,
                                    final String birthDate,
                                    final List<String> medications,
                                    final List<String> allergies) {
        return false;
    }

    public boolean updateMedicalRecord(final String firstName,
                                       final String lastName,
                                       final Optional<String> birthDate,
                                       final Optional<List<String>> medications,
                                       final Optional<List<String>> allergies) {
        return false;
    }

    public boolean deleteMedicalRecord(final String firstName,
                                       final String lastName) {
        return false;
    }

    //    ---------------------------------------------------------------------------------------- PERSON

    public boolean addPerson(final String firstName,
                             final String lastName,
                             final String address,
                             final String city,
                             final String zip,
                             final String phone,
                             final String email) {
        return false;
    }

    public boolean updatePerson(final String firstName,
                                final String lastName,
                                final Optional<String> address,
                                final Optional<String> city,
                                final Optional<String> zip,
                                final Optional<String> phone,
                                final Optional<String> email) {
        return false;
    }

    public boolean deletePerson(final String firstName,
                                final String lastName) {
        return false;
    }

}
