package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.*;

public class PersonFactory {

    private PersonFactory() {
    }

    /**
     * Generates a phone number, format XXX-XXX-XXXX where X is a numeral.
     *
     * @return generated phone number String
     */
    private static String generatePhone() {
        return generateString(NUMBERS, 3) + "-"
                + generateString(NUMBERS, 3) + "-"
                + generateString(NUMBERS, 4);
    }

    /**
     * Generates a Person with randomly generated values for empty optional parameters.
     *
     * @param firstName       Person attribute value (Optional)
     * @param lastName        Person attribute value (Optional)
     * @param completeAddress Addresses enum value value (Optional)
     * @param medicalRecord   Person attribute value (Optional)
     * @return new Person
     */
    public static Person getPerson(Optional<String> firstName,
                                   Optional<String> lastName,
                                   Optional<Addresses> completeAddress,
                                   Optional<MedicalRecord> medicalRecord) {
        if (firstName.isEmpty()) {
            firstName = Optional.of(generateName());
        }

        if (lastName.isEmpty()) {
            lastName = Optional.of(generateName());
        }

        if (completeAddress.isEmpty()) {
            completeAddress = Optional.of(assignAddress());
        }

        String phone = generatePhone();
        String email = firstName.get() + "." + lastName.get() + "@email.com";


        return new Person(firstName.get(),
                lastName.get(),
                completeAddress.get().getName(),
                completeAddress.get().getCity().getName(),
                completeAddress.get().getCity().getZip(),
                phone,
                email)
                .setMedicalRecord(medicalRecord);
    }

    /**
     * Return a list of randomly generated Persons with no MedicalRecord.
     *
     * @param count           number of Person to generate
     * @param lastName        common name String for all Persons
     * @param completeAddress common Addresses enum value for all Persons
     * @return new Person List.
     */
    public static List<Person> getPersonsWithoutMedicalRecord(final int count,
                                                              final Optional<String> lastName,
                                                              final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            persons.add(getPerson(
                    Optional.empty(),
                    lastName,
                    completeAddress,
                    Optional.empty()));
        }
        return persons;
    }

    /**
     * Return a list of randomly generated Persons with MedicalRecord with adult birthdate.
     *
     * @param count           number of Person to generate
     * @param lastName        common name String for all Persons
     * @param completeAddress common Addresses enum value for all Persons
     * @return new Person List.
     */
    public static List<Person> getAdults(final int count,
                                         final Optional<String> lastName,
                                         final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(getPerson(
                    Optional.of(firstName),
                    lastName,
                    completeAddress,
                    Optional.of(MedicalRecordFactory.getMedicalRecord(
                            false,
                            Optional.of(firstName),
                            lastName))));
        }
        return persons;
    }

    /**
     * Return a list of randomly generated Persons with MedicalRecord with child birthdate.
     *
     * @param count           number of Person to generate
     * @param lastName        common name String for all Persons
     * @param completeAddress common Addresses enum value for all Persons
     * @return new Person List.
     */
    public static List<Person> getChildren(final int count,
                                           final Optional<String> lastName,
                                           final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(getPerson(
                    Optional.of(firstName),
                    lastName,
                    completeAddress,
                    Optional.of(MedicalRecordFactory.getMedicalRecord(
                            true,
                            Optional.of(firstName),
                            lastName))));
        }
        return persons;
    }
}
