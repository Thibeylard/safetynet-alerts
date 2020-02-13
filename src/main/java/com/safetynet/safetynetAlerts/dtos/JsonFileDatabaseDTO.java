package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.stereotype.Component;

import java.util.List;

@JsonPropertyOrder({"persons", "firestations", "medicalrecords"})
public class JsonFileDatabaseDTO {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalRecords;

    public JsonFileDatabaseDTO(@JsonProperty("persons") List<Person> pPersons,
                               @JsonProperty("firestations") List<Firestation> pFirestations,
                               @JsonProperty("medicalrecords") List<MedicalRecord> pMedicalRecords) {
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
