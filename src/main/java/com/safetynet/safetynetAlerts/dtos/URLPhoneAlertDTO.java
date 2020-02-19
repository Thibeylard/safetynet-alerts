package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder("phoneNumbers")
public class URLPhoneAlertDTO {
    @JsonProperty("phoneNumbers")
    private List<String> phoneNumberList;

    public URLPhoneAlertDTO(List<String> pPhoneNumberList) {
        this.phoneNumberList = pPhoneNumberList;
    }
}
