package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"adults", "children", "inhabitants"})
public class URLFirestationDTO {

    @JsonProperty("adults")
    private int adults;
    @JsonProperty("children")
    private int children;
    @JsonProperty("inhabitants")
    private List<PersonAddressPhoneDTO> inhabitants;

    public URLFirestationDTO(@JsonProperty("adults") int adults,
                             @JsonProperty("children") int children,
                             @JsonProperty("inhabitants") List<PersonAddressPhoneDTO> inhabitants) {
        this.adults = adults;
        this.children = children;
        this.inhabitants = inhabitants;
    }

    public int getAdults() {
        return adults;
    }

    public int getChildren() {
        return children;
    }

    public List<PersonAddressPhoneDTO> getInhabitants() {
        return inhabitants;
    }
}
