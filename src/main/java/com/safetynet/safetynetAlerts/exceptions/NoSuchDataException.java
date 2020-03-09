package com.safetynet.safetynetAlerts.exceptions;

public class NoSuchDataException extends Exception {

    public NoSuchDataException() {
    }

    @Override
    public String toString() {
        return "NoSuchDataException : Requested data cannot be modified cause they dont exist.";
    }
}
