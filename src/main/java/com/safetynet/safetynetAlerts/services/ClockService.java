package com.safetynet.safetynetAlerts.services;

public interface ClockService {
    int getYear();

    int getMonth();

    int getDay();

    int getAgeFromBirthDate(String birthDate);
}
