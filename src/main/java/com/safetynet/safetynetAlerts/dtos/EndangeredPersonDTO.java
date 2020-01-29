package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"firstName", "lastName", "phone", "age", "medications", "allergies"})
public class EndangeredPersonDTO {

    private final String firstName;
    private final String lastName;
    private final String pPhone;
    private final String pAge;
    private final List<String> pMedications;
    private final List<String> pAllergies;

    public EndangeredPersonDTO(@JsonProperty("firstName") final String pFirstName,
                               @JsonProperty("lastName") final String pLastName,
                               @JsonProperty("phone") final String pPhone,
                               @JsonProperty("age") final String pAge,
                               @JsonProperty("medications") final List<String> pMedications,
                               @JsonProperty("allergies") final List<String> pAllergies) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.pPhone = pPhone;
        this.pAge = pAge;
        this.pMedications = pMedications;
        this.pAllergies = pAllergies;
    }
}