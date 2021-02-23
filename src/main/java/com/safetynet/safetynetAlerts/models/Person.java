package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;

import java.util.Optional;

@JsonPropertyOrder({"firstName", "lastName", "address", "city", "zip", "phone", "email"})
public class Person {

    /**
     * firstName used with lastName as identifier. Final attribute.
     */
    @JsonProperty("firstName")
    private final String firstName;
    /**
     * lastName used with firstName as identifier. Final attribute.
     */
    @JsonProperty("lastName")
    private final String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("city")
    private String city;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonIgnore
    private MedicalRecord medicalRecord;
    @JsonIgnore
    private final Integer age;

    /**
     * Constructor used for JSON deserialization.
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
        this.age = null;
    }

    /**
     * Constructor used to create Person instance associated to its MedicalRecord.
     *
     * @param person        person instance to copy attribute value from.
     * @param medicalRecord associated medicalRecord instance
     * @param age           deduced age from medicalRecord birthdate
     */
    public Person(final Person person,
                  final MedicalRecord medicalRecord,
                  final Integer age) {

        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.address = person.getAddress();
        this.city = person.getCity();
        this.zip = person.getZip();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.medicalRecord = medicalRecord;
        this.age = age;
    }

    /**
     * Constructor used to create Person instance associated to its MedicalRecord.
     *
     * @param firstName     value to initialize firstName attribute
     * @param lastName      value to initialize lastName attribute
     * @param address       value to initialize address attribute
     * @param city          value to initialize city attribute
     * @param zip           value to initialize zip code attribute
     * @param phone         value to initialize phone number attribute
     * @param email         value to initialize email attribute
     * @param medicalRecord associated medicalRecord instance
     * @param age           deduced age from medicalRecord birthdate
     */
    public Person(final String firstName,
                  final String lastName,
                  final String address,
                  final String city,
                  final String zip,
                  final String phone,
                  final String email,
                  final MedicalRecord medicalRecord,
                  final Integer age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.medicalRecord = medicalRecord;
        this.age = age;
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

    /**
     * medicalRecord attribute getter.
     *
     * @return this.medicalRecord
     */
    @JsonIgnore
    public Optional<MedicalRecord> getMedicalRecord() {
        return Optional.ofNullable(medicalRecord);
    }

    /**
     * Age value calculated using current date and birth date.
     *
     * @return age result
     */
    @JsonIgnore
    public Optional<Integer> getAge() {
        return Optional.ofNullable(this.age);
    }

    @JsonIgnore
    public boolean isAdult() throws NoMedicalRecordException {
        return getAge().orElseThrow(() -> new NoMedicalRecordException(this.firstName, this.lastName)) > 18;
    }
}
