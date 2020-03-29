package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

/**
 * DTO used to handle Json Database as an object, helping along manipulations.
 */
@JsonPropertyOrder({"persons", "firestations", "medicalrecords"})
public class JsonFileDatabaseDTO {

    @JsonProperty("persons")
    private List<Person> persons;
    @JsonProperty("firestations")
    private List<Firestation> firestations;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;

    /**
     * Constructor used by Jackson for Json serialization and deserialization.
     *
     * @param persons        attribute value
     * @param firestations   attribute value
     * @param medicalRecords attribute value
     */
    public JsonFileDatabaseDTO(@JsonProperty("persons") List<Person> persons,
                               @JsonProperty("firestations") List<Firestation> firestations,
                               @JsonProperty("medicalrecords") List<MedicalRecord> medicalRecords) {
        this.persons = persons;
        this.firestations = firestations;
        this.medicalRecords = medicalRecords;
    }

    /**
     * Person List database accessor.
     *
     * @return Person List
     */
    public List<Person> getPersons() {
        return persons;
    }

    /**
     * Firestation List database accessor.
     *
     * @return Firestation List
     */
    public List<Firestation> getFirestations() {
        return firestations;
    }

    /**
     * MedicalRecord List database accessor.
     *
     * @return MedicalRecord List
     */
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}
