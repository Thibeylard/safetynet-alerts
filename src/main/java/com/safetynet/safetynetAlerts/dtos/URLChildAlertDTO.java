package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"children", "familyMembers"})
public class URLChildAlertDTO {
    @JsonProperty("children")
    private List<ChildDTO> childDTOList;
    @JsonProperty("familyMembers")
    private List<ChildFamilyMemberDTO> childFamilyMemberDTOS;

    public URLChildAlertDTO(@JsonProperty("children") List<ChildDTO> pChildDTOList,
                            @JsonProperty("familyMembers") List<ChildFamilyMemberDTO> pChildFamilyMemberDTOS) {
        this.childDTOList = pChildDTOList;
        this.childFamilyMemberDTOS = pChildFamilyMemberDTOS;
    }

    public List<ChildDTO> getChildDTOList() {
        return childDTOList;
    }

    public List<ChildFamilyMemberDTO> getChildFamilyMemberDTOS() {
        return childFamilyMemberDTOS;
    }
}
