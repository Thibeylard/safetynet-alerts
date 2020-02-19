package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder("addressResidentsMap")
public class URLFloodDTO {
    @JsonProperty("addressResidentsMap")
    private Map<String, List<EndangeredPersonDTO>> addressResidentsMap;

    public URLFloodDTO(HashMap<String, List<EndangeredPersonDTO>> addressResidentsMap) {
        this.addressResidentsMap = addressResidentsMap;
    }

    public Map<String, List<EndangeredPersonDTO>> getAddressResidentsMap() {
        return addressResidentsMap;
    }
}
