package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"adults","children","inhabitants"})
public class URLFirestationDTO {

    private int adults;
    private int children;
    private List<PersonAddressPhoneDTO> inhabitants;

    public URLFirestationDTO(@JsonProperty("adults") int pAdults,
                             @JsonProperty("children") int pChildren,
                             @JsonProperty("inhabitants") List<PersonAddressPhoneDTO> pInhabitants) {
        this.adults = pAdults;
        this.children = pChildren;
        this.inhabitants = pInhabitants;
    }
}
