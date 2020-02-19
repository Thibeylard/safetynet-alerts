package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;
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
    private Optional<MedicalRecord> medicalRecord;

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
    public Person(final String firstName,
                  final String lastName,
                  final String address,
                  final String city,
                  final String zip,
                  final String phone,
                  final String email,
                  final Optional<MedicalRecord> medicalRecord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
        this.medicalRecord = medicalRecord;
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
    public Optional<MedicalRecord> getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * Age value calculated using current date and birth date.
     *
     * @return age result
     */
    @JsonIgnore
    public int getAge() {
        //TODO Refactored method with date parsing, date as parameter...
        if (medicalRecord.isPresent()) {
            String[] currentDateParts = Instant.now().toString().split("T")[0].split("-");
            // Instant.now date format is yyyy-mm-ddT...Z
            int currentYear = Integer.parseInt(currentDateParts[0]);
            int currentMonth = Integer.parseInt(currentDateParts[1]);
            int currentDay = Integer.parseInt(currentDateParts[2]);

            String[] dateBirthParts = this.medicalRecord.get().getBirthDate().split("/");
            // datebirth format is dd/mm/yyyy
            int birthYear = Integer.parseInt(dateBirthParts[2]);
            int birthMonth = Integer.parseInt(dateBirthParts[1]);
            int birthDay = Integer.parseInt(dateBirthParts[0]);

            boolean yearsBirthdayPassed = (currentMonth > birthMonth) || (currentMonth == birthMonth && currentDay > birthDay);

            return yearsBirthdayPassed ? currentYear - birthYear : currentYear - birthYear - 1;
        } else {
            throw new NullPointerException("Person has no assigned medical record to determine his age.");
        }
    }
}
