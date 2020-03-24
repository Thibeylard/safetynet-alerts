package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"stationNumber", "residents"})
public class URLFireDTO {
    @JsonProperty("stationNumber")
    private int stationNumber;
    @JsonProperty("endangeredPersonDTOList")
    private List<EndangeredPersonDTO> endangeredPersonDTOList;

    public URLFireDTO(@JsonProperty("stationNumber") int stationNumber,
                      @JsonProperty("endangeredPersonDTOList") List<EndangeredPersonDTO> endangeredPersonDTOList) {
        this.stationNumber = stationNumber;
        this.endangeredPersonDTOList = endangeredPersonDTOList;
    }
}
