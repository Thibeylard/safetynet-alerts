package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

@JsonPropertyOrder({"persons", "firestations", "medicalrecords"})
public class JsonFileDatabaseDTO {

    @JsonProperty("persons")
    private List<Person> persons;
    @JsonProperty("firestations")
    private List<Firestation> firestations;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;

    public JsonFileDatabaseDTO(List<Person> pPersons,
                               List<Firestation> pFirestations,
                               List<MedicalRecord> pMedicalRecords) {
        this.persons = pPersons;
        this.firestations = pFirestations;
        this.medicalRecords = pMedicalRecords;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Firestation> getFirestations() {
        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}
