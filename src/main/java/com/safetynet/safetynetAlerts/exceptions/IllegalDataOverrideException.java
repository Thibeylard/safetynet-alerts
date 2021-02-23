package com.safetynet.safetynetAlerts.exceptions;

/**
 * Exception thrown when user tries to create already existant data.
 */
public class IllegalDataOverrideException extends Exception {

    public IllegalDataOverrideException() {
    }

    @Override
    public String toString() {
        return "IllegalDataOverrideException : Data already exist and cannot be override this way.";
    }
}
