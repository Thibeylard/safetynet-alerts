package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

/**
 * DTO used by URLFireDTO and URLFloodDTO to represent Person with assistance related infos.
 */
@JsonPropertyOrder({"firstName", "lastName", "phone", "age", "medications", "allergies"})
public class EndangeredPersonDTO {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("phone")
    private final String phone;
    @JsonProperty("age")
    private final String age;
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
    public EndangeredPersonDTO(Person person) throws NoMedicalRecordException {
        MedicalRecord personMedicalRecord = person.getMedicalRecord().orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName()));
        Integer age = person.getAge().get();

        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.age = age.toString();
        this.medications = personMedicalRecord.getMedications();
        this.allergies = personMedicalRecord.getAllergies();
    }

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param firstName   attribute value
     * @param lastName    attribute value
     * @param phone       attribute value
     * @param age         attribute value
     * @param medications attribute value
     * @param allergies   attribute value
     */
    public EndangeredPersonDTO(final String firstName,
                               final String lastName,
                               final String phone,
                               final String age,
                               final List<String> medications,
                               final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}