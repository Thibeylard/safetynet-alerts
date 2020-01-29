package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName","lastName","age"})
public class ChildDTO {
    private final String firstName;
    private final String lastName;
    private final String age;

    public ChildDTO(@JsonProperty("firstName") final String pFirstName,
                    @JsonProperty("lastName") final String pLastName,
                    @JsonProperty("age") final String pAge) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.age = pAge;
    }
}
