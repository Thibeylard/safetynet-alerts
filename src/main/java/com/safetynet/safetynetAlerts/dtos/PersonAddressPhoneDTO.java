package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

/**
 * DTO used by URLFirestationDTO to represent Person contact infos.
 */
@JsonPropertyOrder({"firstName", "lastName", "address", "phone"})
public class PersonAddressPhoneDTO {
    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("phone")
    private final String phone;

    /**
     * Constructor copying relevant attributes of Person object.
     *
     * @param person Person from which to copy attributes
     */
    public PersonAddressPhoneDTO(final Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param firstName attribute value
     * @param lastName  attribute value
     * @param address   attribute value
     * @param phone     attribute value
     */
    public PersonAddressPhoneDTO(final String firstName,
                                 final String lastName,
                                 final String address,
                                 final String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
}
