package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"citizenEmails"})
public class URLCommunityEmailDTO {
    private final List<String> citizenEmails;

    public URLCommunityEmailDTO(List<String> pCitizenEmails) {
        this.citizenEmails = pCitizenEmails;
    }
}
