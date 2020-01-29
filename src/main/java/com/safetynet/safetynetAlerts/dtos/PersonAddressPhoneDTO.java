package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName","lastName","address","phone"})
public class PersonAddressPhoneDTO {
    private final String lastName;
    private final String firstName;
    private final String address;
    private final String phone;

    public PersonAddressPhoneDTO(@JsonProperty("firstName") final String pFirstName,
                                 @JsonProperty("lastName") final String pLastName,
                                 @JsonProperty("address") final String pAddress,
                                 @JsonProperty("phone") final String pPhone) {
        this.lastName = pLastName;
        this.firstName = pFirstName;
        this.address = pAddress;
        this.phone = pPhone;
    }
}
