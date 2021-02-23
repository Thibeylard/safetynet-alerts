package com.safetynet.safetynetAlerts.services;

public interface ClockService {
    /**
     * Get current Year
     *
     * @return year as integer
     */
    int getYear();

    /**
     * Get current Month
     *
     * @return month as integer
     */
    int getMonth();

    /**
     * Get current Day
     *
     * @return day as integer
     */
    int getDay();

    /**
     * Calculate current age from birthDate
     *
     * @return age as integer
     */
    int getAgeFromBirthDate(String birthDate);
}
