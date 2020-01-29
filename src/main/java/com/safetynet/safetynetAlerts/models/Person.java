package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"firstName", "lastName", "address", "city", "zip", "phone", "email"})
public class Person {


    /**
     * firstName used with lastName as identifier. Final attribute.
     */
    private final String firstName;
    /**
     * lastName used with firstName as identifier. Final attribute.
     */
    private final String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    /**
     * Constructor used for JSON serialization and deserialization.
     *
     * @param firstName value to initialize firstName attribute
     * @param lastName  value to initialize lastName attribute
     * @param address   value to initialize address attribute
     * @param city      value to initialize city attribute
     * @param zip       value to initialize zip code attribute
     * @param phone     value to initialize phone number attribute
     * @param email     value to initialize email attribute
     */
    public Person(@JsonProperty("firstName") final String firstName,
                  @JsonProperty("lastName") final String lastName,
                  @JsonProperty("address") final String address,
                  @JsonProperty("city") final String city,
                  @JsonProperty("zip") final String zip,
                  @JsonProperty("phone") final String phone,
                  @JsonProperty("email") final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    //    -------------------------------------------------------------------- SETTERS

    /**
     * address attribute setter.
     *
     * @param address new value for this.address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * city attribute setter.
     *
     * @param city new value for this.city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * zip attribute setter.
     *
     * @param zip new value for this.zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * phone attribute setter.
     *
     * @param phone new value for this.phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * email attribute setter.
     *
     * @param email new value for this.email
     */
    public void setEmail(String email) {
        this.email = email;
    }


    //    -------------------------------------------------------------------- GETTERS

    /**
     * firstName attribute getter.
     *
     * @return this.firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * lastName attribute getter.
     *
     * @return this.lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * address attribute getter.
     *
     * @return this.address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * city attribute getter.
     *
     * @return this.city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * zip attribute getter.
     *
     * @return this.zip
     */
    public String getZip() {
        return this.zip;
    }

    /**
     * phone attribute getter.
     *
     * @return this.phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * email attribute getter.
     *
     * @return this.email
     */
    public String getEmail() {
        return this.email;
    }
}
