package com.safetynet.safetynetAlerts.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.models.Person;

/**
 * DTO used by URLChildAlertDTO to represent adults living with children
 */
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

    /**
     * Constructor copying relevant attributes of Person object.
     *
     * @param person Person from which to copy attributes
     */
    public ChildFamilyMemberDTO(Person person) {
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
    }

    /**
     * Constructor used by Jackson for Json serialization.
     *
     * @param firstName attribute value
     * @param lastName  attribute value
     * @param phone     attribute value
     * @param email     attribute value
     */
    public ChildFamilyMemberDTO(final String firstName,
                                final String lastName,
                                final String phone,
                                final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
}
