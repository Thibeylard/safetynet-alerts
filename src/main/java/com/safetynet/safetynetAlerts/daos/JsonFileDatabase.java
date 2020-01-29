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

    public List<Person> getPersons() {
        return this.safetynetAlertsData.persons;
    }

    public List<Firestation> getFirestations() {
        return this.safetynetAlertsData.firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return this.safetynetAlertsData.medicalRecords;
    }

    public boolean addFirestation(Firestation firestation) {
        return false;
    }

    public boolean addMedicalRecord(MedicalRecord medicalRecord) {
        return false;
    }

    public boolean addPerson(Person person) {
        return false;
    }

    public boolean updateFirestation(Firestation firestation) {
        return false;
    }

    public boolean updateMedicalRecord(MedicalRecord medicalRecord) {
        return false;
    }

    public boolean updatePerson(Person person) {
        return false;
    }

    public Firestation getFirestation(String address) {

        return null;
    }

    public List<String> getFirestationAddresses(int pNumber) {
        return null;
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        return null;
    }

    public String getPersonAge(String firstName, String lastName) {
        return null;
    }

    public MedicalRecord getPersonMedicalRecord(String firstName, String lastName) {
        return null;
    }

    public Person getPerson(String firstName, String lastName) {
        return null;
    }

    public Person getFromName(final String pFirstName, final String pLastName) {
        return null;
    }

    public List<Person> getFromAddress(final String pAddress) {
        return null;
    }

}
