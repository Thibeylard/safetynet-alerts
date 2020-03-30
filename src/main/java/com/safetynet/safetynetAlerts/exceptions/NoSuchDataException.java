package com.safetynet.safetynetAlerts.exceptions;

/**
 * Exception thrown when user tries to access some data that does not exist in database.
 */
public class NoSuchDataException extends Exception {

    public NoSuchDataException() {
    }

    @Override
    public String toString() {
        return "NoSuchDataException : Requested data cannot be modified cause they dont exist.";
    }
}
