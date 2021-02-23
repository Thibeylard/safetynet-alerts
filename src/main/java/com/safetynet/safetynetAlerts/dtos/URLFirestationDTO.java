package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * DTO used to represents all Person depending on a Firestation
 */
@JsonPropertyOrder({"adults", "children", "inhabitants"})
public class URLFirestationDTO {

    @JsonProperty("adults")
    private int adults;
    @JsonProperty("children")
    private int children;
    @JsonProperty("inhabitants")
    private List<PersonAddressPhoneDTO> inhabitants;

    /**
     * Constructor used by Jackson for Json serialization
     *
     * @param adults      number of adults among inhabitants
     * @param children    number of children among inhabitants
     * @param inhabitants List of Person, adults and children
     */
    public URLFirestationDTO(@JsonProperty("adults") int adults,
                             @JsonProperty("children") int children,
                             @JsonProperty("inhabitants") List<PersonAddressPhoneDTO> inhabitants) {
        this.adults = adults;
        this.children = children;
        this.inhabitants = inhabitants;
    }

    /**
     * URLFirestationDTO adults number accessor
     *
     * @return number of adults
     */
    public int getAdults() {
        return adults;
    }

    /**
     * URLFirestationDTO children number accessor
     *
     * @return number of children
     */
    public int getChildren() {
        return children;
    }

    /**
     * URLFirestationDTO Person List accessor
     *
     * @return List of Person
     */
    public List<PersonAddressPhoneDTO> getInhabitants() {
        return inhabitants;
    }
}
