package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * DTO used to represent all inhabitants phone numbers depending on specific Firestation number.
 */
@JsonPropertyOrder("phoneNumbers")
public class URLPhoneAlertDTO {
    @JsonProperty("phoneNumbers")
    private List<PersonPhoneDTO> phoneNumberList;

    /**
     * Default Constructor, used by Jackson for Json serialization
     *
     * @param pPhoneNumberList List of PersonPhoneDTO
     */
    public URLPhoneAlertDTO(@JsonProperty("phoneNumbers") List<PersonPhoneDTO> pPhoneNumberList) {
        this.phoneNumberList = pPhoneNumberList;
    }
}
