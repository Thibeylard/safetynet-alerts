package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"citizenEmails"})
public class URLCommunityEmailDTO {

    @JsonProperty("citizenEmails")
    private List<String> citizenEmails;

    public URLCommunityEmailDTO(@JsonProperty("citizenEmails") List<String> citizensEmails) {
        this.citizenEmails = citizensEmails;
    }

    public URLCommunityEmailDTO() {
        this.citizenEmails = null;
    }

    public URLCommunityEmailDTO withPersonsEmails(List<Person> citizens) {
        this.citizenEmails = new ArrayList<>();
        citizens.forEach(citizen -> this.citizenEmails.add(citizen.getEmail()));
        return this;
    }
}
