package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder("address")
public class URLFloodDTO {
    private Map<String, List<EndangeredPersonDTO>> addressResidentsMap;

    public URLFloodDTO() {
        this.addressResidentsMap = new HashMap<String, List<EndangeredPersonDTO>>();
    }

    public Map<String, List<EndangeredPersonDTO>> getAddressResidentsMap() {
        return addressResidentsMap;
    }
}
