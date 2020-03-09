package com.safetynet.safetynetAlerts.services;

import java.time.Clock;
import java.time.temporal.ChronoField;

public class ClockServiceImpl implements ClockService {

    private Clock clock;

    public ClockServiceImpl() {
        this.clock = Clock.systemUTC();
    }

    public int getYear() {
        return clock.instant().atZone(clock.getZone()).get(ChronoField.YEAR);
    }

    public int getMonth() {
        return clock.instant().atZone(clock.getZone()).get(ChronoField.MONTH_OF_YEAR);
    }

    public int getDay() {
        return clock.instant().atZone(clock.getZone()).get(ChronoField.DAY_OF_MONTH);
    }

    @Override
    public int getAgeFromBirthDate(final String birthDate) {
        int currentYear = getYear();
        int currentMonth = getMonth();
        int currentDay = getDay();

        //TODO Check String format and throw Exception in case of wrong birthdate ?
        String[] dateBirthParts = birthDate.split("/");
        // datebirth format is dd/mm/yyyy
        int birthYear = Integer.parseInt(dateBirthParts[2]);
        int birthMonth = Integer.parseInt(dateBirthParts[1]);
        int birthDay = Integer.parseInt(dateBirthParts[0]);

        boolean yearsBirthdayPassed = (currentMonth > birthMonth) || (currentMonth == birthMonth && currentDay > birthDay);

        return yearsBirthdayPassed ? currentYear - birthYear : currentYear - birthYear - 1;
    }
}
