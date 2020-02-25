package com.safetynet.safetynetAlerts.exceptions;

public class IllegalDataOverrideException extends Exception {

    public IllegalDataOverrideException() {
    }

    @Override
    public String toString() {
        return "IllegalDataOverrideException : Data already exist and cannot be override this way.";
    }
}
