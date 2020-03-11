package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.services.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.*;

@Repository
public class MedicalRecordFactory {

    private static ClockService clock;

    @Autowired
    private MedicalRecordFactory(ClockService clockService) {
        clock = clockService;
    }

    /**
     * Generate random birthDate with format : dd/mm/YYYY.
     *
     * @param isChild if true, birth year is at least 2003.
     * @return birthDate String
     */
    private static String generateBirthDate(boolean isChild) {
        String birthDate = (getRandom().nextInt(31) + 1) + "/"
                + (getRandom().nextInt(12) + 1) + "/";
        if (isChild) {
            birthDate += clock.getYear() - (getRandom().nextInt(17));
        } else {
            birthDate += clock.getYear() - 20 - (getRandom().nextInt(50));
        }
        return birthDate;
    }

    /**
     * Generate a list of random medications, with format : medicine:posology
     *
     * @return medications String List
     */
    private static List<String> generateMedications() {
        int count = getRandom().nextInt(3);
        List<String> medications = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            medications.add(generateString(LETTERS, getRandom().nextInt(5) + 5) + ":" + generateString(NUMBERS, 3) + "mg");
        }

        return medications;
    }

    /**
     * Generate a list of random allergies.
     *
     * @return allergies String List
     */
    private static List<String> generateAllergies() {
        int count = getRandom().nextInt(3);
        List<String> allergies = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            allergies.add(generateString(LETTERS, getRandom().nextInt(5) + 5));
        }

        return allergies;
    }

    /**
     * Generates a MedicalRecord with randomly generated values.
     *
     * @param isChild birthDate will correspond to a less than 18 year old
     * @return new MedicalRecord
     */
    public static MedicalRecord createMedicalRecord(boolean isChild) {
        return createMedicalRecord(null, null, isChild);
    }

    /**
     * Generates a MedicalRecord with randomly generated values for empty optional parameters.
     *
     * @param firstName MedicalRecord attribute value (Nullable)
     * @param lastName  MedicalRecord attribute value (Nullable)
     * @param isChild   birthDate will correspond to a less than 18 year old
     * @return new MedicalRecord
     */
    public static MedicalRecord createMedicalRecord(@Nullable String firstName,
                                                    @Nullable String lastName,
                                                    boolean isChild) {
        if (firstName == null) {
            firstName = generateName();
        }

        if (lastName == null) {
            lastName = generateName();
        }

        String birthDate = generateBirthDate(isChild);
        List<String> medications = generateMedications();
        List<String> allergies = generateAllergies();

        return new MedicalRecord(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * Generates a MedicalRecord with randomly generated values for empty optional parameters.
     *
     * @param firstName   MedicalRecord attribute value (Nullable)
     * @param lastName    MedicalRecord attribute value (Nullable)
     * @param birthDate   MedicalRecord attribute value (Nullable)
     * @param medications MedicalRecord attribute value (Nullable)
     * @param allergies   MedicalRecord attribute value (Nullable)
     * @param isChild     birthDate will correspond to a less than 18 year old
     * @return new MedicalRecord
     */
    public static MedicalRecord createMedicalRecord(@Nullable String firstName,
                                                    @Nullable String lastName,
                                                    @Nullable String birthDate,
                                                    @Nullable List<String> medications,
                                                    @Nullable List<String> allergies,
                                                    boolean isChild) {
        if (firstName == null) {
            firstName = generateName();
        }

        if (lastName == null) {
            lastName = generateName();
        }

        if (birthDate == null) {
            birthDate = generateBirthDate(isChild);
        }

        if (medications == null) {
            medications = generateMedications();
        }
        if (allergies == null) {
            allergies = generateAllergies();
        }

        return new MedicalRecord(firstName, lastName, birthDate, medications, allergies);
    }
}
