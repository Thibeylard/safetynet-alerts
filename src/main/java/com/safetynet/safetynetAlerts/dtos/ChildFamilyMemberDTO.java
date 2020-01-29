package com.safetynet.safetynetAlerts.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName", "lastName", "phone", "email"})
public class ChildFamilyMemberDTO {

    private final String firstName;
    private final String lastName;
    private final String phone;
    private final String email;

    public ChildFamilyMemberDTO(@JsonProperty("firstName") final String pFirstName,
                                @JsonProperty("lastName") final String pLastName,
                                @JsonProperty("phone") final String pPhone,
                                @JsonProperty("email") final String pEmail) {
        this.firstName = pFirstName;
        this.lastName = pLastName;
        this.phone = pPhone;
        this.email = pEmail;
    }
}
