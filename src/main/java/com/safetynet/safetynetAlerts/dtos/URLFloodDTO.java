package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Map;

/**
 * DTO used to represents all Persons depending on multiple Firestation grouped by address
 */
@JsonPropertyOrder("addressResidentsMap")
public class URLFloodDTO {
    @JsonProperty("addressResidentsMap")
    private Map<String, List<EndangeredPersonDTO>> addressResidentsMap;

    /**
     * Default Constructor, used by Jackson for Json serialization.
     *
     * @param addressResidentsMap Map withString key and EndangeredPersonDTO List value
     */
    public URLFloodDTO(@JsonProperty("addressResidentsMap") Map<String, List<EndangeredPersonDTO>> addressResidentsMap) {
        this.addressResidentsMap = addressResidentsMap;
    }
}
