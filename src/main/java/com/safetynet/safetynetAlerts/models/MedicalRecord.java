package com.safetynet.safetynetAlerts.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@JsonPropertyOrder({"firstName", "lastName", "birthDate", "medications", "allergies"})
public class MedicalRecord {

    /**
     * firstName used with lastName as identifier. Final attribute.
     */
    private final String firstName;
    /**
     * lastName used with firstName as identifier. Final attribute.
     */
    private final String lastName;
    private String birthDate;
    private List<String> medications;
    private List<String> allergies;

    /**
     * Constructor used for JSON serialization and deserialization.
     *
     * @param firstName   value to initialize firstName attribute
     * @param lastName    value to initialize lastName attribute
     * @param birthDate   value to initialize birthDate attribute
     * @param medications value to initialize medications attribute
     * @param allergies   value to initialize allergies attribute
     */
    public MedicalRecord(@JsonProperty("firstName") final String firstName,
                         @JsonProperty("lastName") final String lastName,
                         @JsonProperty("birthdate") final String birthDate,
                         @JsonProperty("medications") final List<String> medications,
                         @JsonProperty("allergies") final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    //    -------------------------------------------------------------------- SETTERS

    /**
     * birthDate attribute setter.
     *
     * @param birthDate new value for this.birthDate
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * medications attribute setter.
     *
     * @param medications new value for this.medications
     */
    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    /**
     * allergies attribute setter.
     *
     * @param allergies new value for this.allergies
     */
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
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
     * birthDate attribute getter.
     *
     * @return this.birthDate
     */
    public String getBirthDate() {
        return this.birthDate;
    }

    /**
     * Age value calculated using current date and birth date.
     *
     * @return age result
     */
    public int getAge() {
        String[] currentDateParts = Instant.now().toString().split("T")[0].split("-");
        // Instant.now date format is yyyy-mm-ddT...Z
        int currentYear = Integer.parseInt(currentDateParts[0]);
        int currentMonth = Integer.parseInt(currentDateParts[1]);
        int currentDay = Integer.parseInt(currentDateParts[2]);

        String[] dateBirthParts = this.birthDate.split("/");
        // datebirth format is dd/mm/yyyy
        int birthYear = Integer.parseInt(dateBirthParts[2]);
        int birthMonth = Integer.parseInt(dateBirthParts[1]);
        int birthDay = Integer.parseInt(dateBirthParts[0]);

        boolean yearsBirthdayPassed = (currentMonth > birthMonth) || (currentMonth == birthMonth && currentDay > birthDay);

        return yearsBirthdayPassed ? currentYear - birthYear : currentYear - birthYear - 1;
    }

    /**
     * medications attribute getter.
     *
     * @return this.medications
     */
    public List<String> getMedications() {
        return this.medications;
    }

    /**
     * allergies attribute getter.
     *
     * @return this.allergies
     */
    public List<String> getAllergies() {
        return this.allergies;
    }
}
