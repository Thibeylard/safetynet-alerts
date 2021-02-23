package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO used to represent all inhabitants emails of a specific city
 */
@JsonPropertyOrder({"citizenEmails"})
public class URLCommunityEmailDTO {

    @JsonProperty("citizenEmails")
    private List<String> citizenEmails;

    /**
     * Constructor used by Jackson for Json serialization
     *
     * @param citizensEmails List of String
     */
    public URLCommunityEmailDTO(@JsonProperty("citizenEmails") List<String> citizensEmails) {
        this.citizenEmails = citizensEmails;
    }

    /**
     * Default constructor
     */
    public URLCommunityEmailDTO() {
        this.citizenEmails = null;
    }

    /**
     * Build method to define citizenEmails attribute based on Person List.
     *
     * @param citizens List of Person
     * @return URLCommunityEmailDTO updated
     */
    public URLCommunityEmailDTO withPersonsEmails(List<Person> citizens) {
        this.citizenEmails = new ArrayList<>();
        citizens.forEach(citizen -> this.citizenEmails.add(citizen.getEmail()));
        return this;
    }
}
