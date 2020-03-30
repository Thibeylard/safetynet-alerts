package com.safetynet.safetynetAlerts.exceptions;

/**
 * Exception thrown when user tries to access Person MedicalRecord that is not defined.
 */
public class NoMedicalRecordException extends Exception {

    private String personFirstName;
    private String personLastName;

    public NoMedicalRecordException(String firstName, String lastName) {
        this.personFirstName = firstName;
        this.personLastName = lastName;
    }

    @Override
    public String toString() {
        return "NoMedicalRecordException :" + this.personFirstName +
                " " + this.personLastName + " Person has no assigned medical record to be accessed";
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }
}
