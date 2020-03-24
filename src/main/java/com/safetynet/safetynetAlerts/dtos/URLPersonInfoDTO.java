package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

@JsonPropertyOrder({"firstName", "lastName", "address", "age", "email", "medications", "allergies"})
public class URLPersonInfoDTO {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("age")
    private final String age;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("medications")
    private final List<String> medications;
    @JsonProperty("allergies")
    private final List<String> allergies;

    public URLPersonInfoDTO(@JsonProperty("firstName") final String firstName,
                            @JsonProperty("lastName") final String lastName,
                            @JsonProperty("address") final String address,
                            @JsonProperty("age") final String age,
                            @JsonProperty("email") final String email,
                            @JsonProperty("medications") final List<String> medications,
                            @JsonProperty("allergies") final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    public URLPersonInfoDTO(final Person person) throws NoMedicalRecordException {
        MedicalRecord medicalRecord = person.getMedicalRecord().orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName()));
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getLastName();
        this.age = String.valueOf(person.getAge());
        this.email = person.getEmail();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }
}


