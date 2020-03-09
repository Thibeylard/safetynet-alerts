package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

@JsonPropertyOrder({"firstName", "lastName", "address", "phone"})
public class PersonAddressPhoneDTO {
    @JsonProperty("firstName")
    private final String lastName;
    @JsonProperty("lastName")
    private final String firstName;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("phone")
    private final String phone;

    public PersonAddressPhoneDTO(final String pFirstName,
                                 final String pLastName,
                                 final String pAddress,
                                 final String pPhone) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.address = pAddress;
        this.phone = pPhone;
    }

    public PersonAddressPhoneDTO(final Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getFirstName();
        this.address = person.getAddress();
        this.phone = person.getPhone();
    }
}
