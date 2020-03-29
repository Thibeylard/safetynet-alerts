package com.safetynet.safetynetAlerts.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * DTO used to identify children and their family of a specific address
 */
@JsonPropertyOrder({"children", "familyMembers"})
public class URLChildAlertDTO {
    @JsonProperty("children")
    private List<ChildDTO> childDTOList;
    @JsonProperty("familyMembers")
    private List<ChildFamilyMemberDTO> childFamilyMemberDTOS;

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param childDTOList          List of ChildDTO
     * @param childFamilyMemberDTOS List of ChildFamilyMemberDTO
     */
    public URLChildAlertDTO(@JsonProperty("children") List<ChildDTO> childDTOList,
                            @JsonProperty("familyMembers") List<ChildFamilyMemberDTO> childFamilyMemberDTOS) {
        this.childDTOList = childDTOList;
        this.childFamilyMemberDTOS = childFamilyMemberDTOS;
    }

    /**
     * URLChildAlertDTO childDTO List accessor
     *
     * @return List of ChildDTO
     */
    public List<ChildDTO> getChildDTOList() {
        return childDTOList;
    }

    /**
     * URLChildAlertDTO childFamilyMembersDTO List accessor
     *
     * @return List of ChildFamilyMembersDTO
     */
    public List<ChildFamilyMemberDTO> getChildFamilyMemberDTOS() {
        return childFamilyMemberDTOS;
    }
}
