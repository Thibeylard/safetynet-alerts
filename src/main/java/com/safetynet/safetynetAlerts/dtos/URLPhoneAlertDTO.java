package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder("phoneNumbers")
public class URLPhoneAlertDTO {
    @JsonProperty("phoneNumbers")
    private List<PersonPhoneDTO> phoneNumberList;

    public URLPhoneAlertDTO(@JsonProperty("phoneNumbers") List<PersonPhoneDTO> pPhoneNumberList) {
        this.phoneNumberList = pPhoneNumberList;
    }
}
