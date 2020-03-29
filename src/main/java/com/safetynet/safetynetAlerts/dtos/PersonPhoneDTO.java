package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

/**
 * DTO used by URLPhoneAlertDTO to represent Person phone contact.
 */
@JsonPropertyOrder({"firstName", "lastName", "phone"})
public class PersonPhoneDTO {

    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("phone")
    private final String phone;

    /**
     * Constructor copying relevant attributes of Person object.
     *
     * @param person Person from which to copy attributes
     */
    public PersonPhoneDTO(final Person person) {
        this.lastName = person.getLastName();
        this.firstName = person.getFirstName();
        this.phone = person.getPhone();
    }

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param firstName attribute value
     * @param lastName  attribute value
     * @param phone     attribute value
     */
    public PersonPhoneDTO(final String firstName,
                          final String lastName,
                          final String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }
}
