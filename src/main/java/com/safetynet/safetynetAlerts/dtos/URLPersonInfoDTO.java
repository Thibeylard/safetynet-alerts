package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"firstName","lastName","address","age","email","medications","allergies"})
public class URLPersonInfoDTO {

    private final String firstName;
    private final String lastName;
    private final String pAddress;
    private final String pAge;
    private final String pEmail;
    private final List<String> pMedications;
    private final List<String> pAllergies;

    public URLPersonInfoDTO(@JsonProperty("firstName") String pFirstName,
                            @JsonProperty("lastName") String pLastName,
                            @JsonProperty("address") String pAddress,
                            @JsonProperty("age") String pAge,
                            @JsonProperty("email") String pEmail,
                            @JsonProperty("medications") List<String> pMedications,
                            @JsonProperty("allergies") List<String> pAllergies) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.pAddress = pAddress;
        this.pAge = pAge;
        this.pEmail = pEmail;
        this.pMedications = pMedications;
        this.pAllergies = pAllergies;
    }
}


