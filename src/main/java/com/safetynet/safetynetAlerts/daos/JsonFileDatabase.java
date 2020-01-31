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

    //    ---------------------------------------------------------------------------------------- Firestation

    public boolean addFirestation(Firestation firestation) throws IOException {
        return false;
    }

    public boolean updateFirestation(Firestation firestation) throws IOException {
        return false;
    }

    public List<Firestation> getFirestations() throws IOException {
        return this.safetynetAlertsData.firestations;
    }

    public Firestation getFirestation(String address) throws IOException {

        return null;
    }

    public List<String> getFirestationAddresses(int number) throws IOException {
        return null;
    }

    //    ---------------------------------------------------------------------------------------- MedicalRecord

    public boolean addMedicalRecord(MedicalRecord medicalRecord) throws IOException {
        return false;
    }

    public boolean updateMedicalRecord(MedicalRecord medicalRecord) throws IOException {
        return false;
    }

    public List<MedicalRecord> getMedicalRecords() throws IOException {
        return this.safetynetAlertsData.medicalRecords;
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) throws IOException {
        return null;
    }

    public MedicalRecord getPersonMedicalRecord(String firstName, String lastName) throws IOException {
        return null;
    }

    public String getPersonAge(String firstName, String lastName) throws IOException {
        return null;
    }

    public List<MedicalRecord> getAdultMedicalRecords() throws IOException {
        return null;
    }

    public List<MedicalRecord> getChildrenMedicalRecords() throws IOException {
        return null;
    }



    //    ---------------------------------------------------------------------------------------- Person

    public boolean addPerson(Person person) throws IOException {
        return false;
    }

    public boolean updatePerson(Person person) throws IOException {
        return false;
    }

    public List<Person> getPersons() throws IOException {
        return this.safetynetAlertsData.persons;
    }

    public Person getPerson(String firstName, String lastName) throws IOException {
        return null;
    }

    public Person getFromName(final String firstName, final String lastName) throws IOException {
        return null;
    }

    public List<Person> getFromAddress(final String address) throws IOException {
        return null;
    }

    public List<Person> getFromName(final String lastName) throws IOException  {
        return null;
    }

    public List<Person> getCommunity(final String city) throws IOException {
        return null;
    }

}
