package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"firstName","lastName","address","age","email","medications","allergies"})
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

    public URLPersonInfoDTO(final String firstName,
                            final String lastName,
                            final String address,
                            final String age,
                            final String email,
                            final List<String> medications,
                            final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }
}


