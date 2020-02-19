package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"firstName", "lastName", "phone", "age", "medications", "allergies"})
public class EndangeredPersonDTO {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("pPhone")
    private final String phone;
    @JsonProperty("age")
    private final String age;
    @JsonProperty("medications")
    private final List<String> medications;
    @JsonProperty("allergies")
    private final List<String> allergies;

    public EndangeredPersonDTO(final String pFirstName,
                               final String pLastName,
                               final String phone,
                               final String age,
                               final List<String> medications,
                               final List<String> allergies) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }
}