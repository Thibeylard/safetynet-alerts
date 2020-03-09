package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"citizenEmails"})
public class URLCommunityEmailDTO {
    @JsonProperty("citizenEmails")
    private final List<String> citizenEmails;

    public URLCommunityEmailDTO(List<Person> citizens) {
        this.citizenEmails = new ArrayList<>();
        citizens.forEach(citizen -> this.citizenEmails.add(citizen.getEmail()));
    }
}
