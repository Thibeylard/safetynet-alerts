package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName","lastName","address","phone"})
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
        this.lastName = pLastName;
        this.firstName = pFirstName;
        this.address = pAddress;
        this.phone = pPhone;
    }
}
