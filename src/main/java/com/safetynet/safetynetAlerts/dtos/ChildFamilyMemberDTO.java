package com.safetynet.safetynetAlerts.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

@JsonPropertyOrder({"firstName", "lastName", "phone", "email"})
public class ChildFamilyMemberDTO {

    @JsonProperty("firstName")
    private final String firstName;
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("phone")
    private final String phone;
    @JsonProperty("email")
    private final String email;

    public ChildFamilyMemberDTO(final String firstName,
                                final String lastName,
                                final String phone,
                                final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public ChildFamilyMemberDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
    }
}
