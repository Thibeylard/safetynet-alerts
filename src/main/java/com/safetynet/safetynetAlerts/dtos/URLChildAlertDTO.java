package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"children","familyMembers"})
public class URLChildAlertDTO {
    private List<ChildDTO> childDTOList;
    private List<ChildFamilyMemberDTO> childFamilyMemberDTOS;

    public URLChildAlertDTO(@JsonProperty("children") List<ChildDTO> pChildDTOList,
                            @JsonProperty("familyMembers") List<ChildFamilyMemberDTO> pChildFamilyMemberDTOS) {
        this.childDTOList = pChildDTOList;
        this.childFamilyMemberDTOS = pChildFamilyMemberDTOS;
    }
}
