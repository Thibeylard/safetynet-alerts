package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.Person;

/**
 * DTO used by URLChildAlertDTO to represent an underaged inhabitant
 */
@JsonPropertyOrder({"firstName", "lastName", "age"})
public class ChildDTO {
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("age")
    private final String age;

    /**
     * Constructor copying relevant attributes of Person object.
     *
     * @param person Person from which to copy attributes
     * @throws NoMedicalRecordException if Person has no MedicalRecord (meaning age cannot be found)
     */
    public ChildDTO(Person person) throws NoMedicalRecordException {
        Integer age = person.getAge()
                .orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName()));
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = age.toString();
    }

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param firstName attribute value
     * @param lastName  attribute value
     * @param age       attribute value
     */
    public ChildDTO(final String firstName,
                    final String lastName,
                    final String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
