package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.models.MedicalRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.*;

public class MedicalRecordFactory {

    private MedicalRecordFactory() {
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
            birthDate += (getRandom().nextInt(17) + 2003);
        } else {
            birthDate += (getRandom().nextInt(52) + 1950);
        }
        return birthDate;
    }

    /**
     * Generate a list of random medications, with format : medicine:posology
     *
     * @return medications String List
     */
    private static List<String> generateMedications() {
        int count = getRandom().nextInt(3) + 1;
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
        int count = getRandom().nextInt(3) + 1;
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
    public static MedicalRecord getMedicalRecord(boolean isChild) {
        return getMedicalRecord(isChild, Optional.empty(), Optional.empty());
    }

    /**
     * Generates a MedicalRecord with randomly generated values for empty optional parameters.
     *
     * @param isChild   birthDate will correspond to a less than 18 year old
     * @param firstName MedicalRecord attribute value (Optional)
     * @param lastName  MedicalRecord attribute value (Optional)
     * @return new MedicalRecord
     */
    public static MedicalRecord getMedicalRecord(boolean isChild,
                                                 Optional<String> firstName,
                                                 Optional<String> lastName) {
        if (firstName.isEmpty()) {
            firstName = Optional.of(generateName());
        }

        if (lastName.isEmpty()) {
            lastName = Optional.of(generateName());
        }

        String birthDate = generateBirthDate(isChild);
        List<String> medications = generateMedications();
        List<String> allergies = generateAllergies();

        return new MedicalRecord(firstName.get(), lastName.get(), birthDate, medications, allergies);
    }

    /**
     * Generates a MedicalRecord with randomly generated values for empty optional parameters.
     *
     * @param isChild     birthDate will correspond to a less than 18 year old
     * @param firstName   MedicalRecord attribute value (Optional)
     * @param lastName    MedicalRecord attribute value (Optional)
     * @param birthDate   MedicalRecord attribute value (Optional)
     * @param medications MedicalRecord attribute value (Optional)
     * @param allergies   MedicalRecord attribute value (Optional)
     * @return new MedicalRecord
     */
    public static MedicalRecord getMedicalRecord(boolean isChild,
                                                 Optional<String> firstName,
                                                 Optional<String> lastName,
                                                 Optional<String> birthDate,
                                                 Optional<List<String>> medications,
                                                 Optional<List<String>> allergies) {
        if (firstName.isEmpty()) {
            firstName = Optional.of(generateName());
        }

        if (lastName.isEmpty()) {
            lastName = Optional.of(generateName());
        }

        if (birthDate.isEmpty()) {
            birthDate = Optional.of(generateBirthDate(isChild));
        }

        if (medications.isEmpty()) {
            medications = Optional.of(generateMedications());
        }
        if (allergies.isEmpty()) {
            allergies = Optional.of(generateAllergies());
        }

        return new MedicalRecord(firstName.get(), lastName.get(), birthDate.get(), medications.get(), allergies.get());
    }
}
