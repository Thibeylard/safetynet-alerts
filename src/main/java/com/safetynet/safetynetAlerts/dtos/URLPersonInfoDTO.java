package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

/**
 * DTO used to represent a complete set of relevant infos on a Person (contact and assistance)
 */
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

    /**
     * Constructor copying relevant attributes of Person object.
     *
     * @param person Person from which to copy attributes
     * @throws NoMedicalRecordException if Person has no MedicalRecord (meaning age, medications and allergies cannot be found)
     */
    public URLPersonInfoDTO(final Person person) throws NoMedicalRecordException {
        MedicalRecord medicalRecord = person.getMedicalRecord()
                .orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName()));
        Integer age = person.getAge().get();

        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.age = age.toString();
        this.email = person.getEmail();
        this.medications = medicalRecord.getMedications();
        this.allergies = medicalRecord.getAllergies();
    }

    /**
     * Constructor used by Jackson for Json serialization
     *
     * @param firstName   attribute value
     * @param lastName    attribute value
     * @param address     attribute value
     * @param age         attribute value
     * @param email       attribute value
     * @param medications attribute value
     * @param allergies   attribute value
     */
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
}


