package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

@JsonPropertyOrder({"firstName", "lastName", "address", "phone"})
public class PersonPhoneDTO {

    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("phone")
    private final String phone;

    public PersonPhoneDTO(final String firstName,
                          final String lastName,
                          final String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public PersonPhoneDTO(final Person person) {
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.phone = person.getPhone();
    }
}
