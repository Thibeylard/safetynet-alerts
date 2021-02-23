package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * DTO used to identify possible endangered Persons depending on Firestation stationNumber
 */
@JsonPropertyOrder({"stationNumber", "residents"})
public class URLFireDTO {
    @JsonProperty("stationNumber")
    private int stationNumber;
    @JsonProperty("endangeredPersonDTOList")
    private List<EndangeredPersonDTO> endangeredPersonDTOList;

    /**
     * Default Constructor, used by Jackson for Json serialization
     *
     * @param stationNumber           Related Firestation stationNumber
     * @param endangeredPersonDTOList Persons depending of Firestation
     */
    public URLFireDTO(@JsonProperty("stationNumber") int stationNumber,
                      @JsonProperty("endangeredPersonDTOList") List<EndangeredPersonDTO> endangeredPersonDTOList) {
        this.stationNumber = stationNumber;
        this.endangeredPersonDTOList = endangeredPersonDTOList;
    }
}
