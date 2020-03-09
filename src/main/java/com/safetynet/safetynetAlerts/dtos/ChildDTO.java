package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.models.Person;

@JsonPropertyOrder({"firstName", "lastName", "age"})
public class ChildDTO {
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("age")
    private final String age;

    public ChildDTO(final String firstName,
                    final String lastName,
                    final String age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public ChildDTO(Person person) throws NoMedicalRecordException {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.age = String.valueOf(person.getAge());
    }
}
