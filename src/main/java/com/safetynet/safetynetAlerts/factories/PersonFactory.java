package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import com.safetynet.safetynetAlerts.services.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetAlerts.factories.UtilsFactory.*;

@Repository
public class PersonFactory {

    private static ClockService clock;

    @Autowired
    private PersonFactory(ClockService clockService) {
        clock = clockService;
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
     * Build a Person with its MedicalRecord and its age.
     *
     * @param person        Person instance base (no MedicalRecord neither age)
     * @param medicalRecord MedicalRecord instance to assign to person
     * @return new Person
     */
    public static Person buildPerson(Person person, MedicalRecord medicalRecord) {
        return new Person(person, medicalRecord, clock.getAgeFromBirthDate(medicalRecord.getBirthDate()));
    }

    /**
     * Generates a Person with randomly generated values.
     *
     * @return new Person
     */
    public static Person createPerson() {
        return createPerson(null, null, null, null);
    }

    /**
     * Generates a Person with randomly generated values for empty optional parameters.
     *
     * @param firstName       Person attribute value (Nullable)
     * @param lastName        Person attribute value (Nullable)
     * @param completeAddress Addresses enum value value (Nullable)
     * @param medicalRecord   Person attribute value (Nullable)
     * @return new Person
     */
    public static Person createPerson(@Nullable String firstName,
                                      @Nullable String lastName,
                                      @Nullable Addresses completeAddress,
                                      @Nullable MedicalRecord medicalRecord) {
        if (firstName == null)
            firstName = generateName();
        if (lastName == null)
            lastName = generateName();
        if (completeAddress == null) {
            completeAddress = assignAddress();
        }

        String email = firstName + "." + lastName + "@email.com";
        Integer age;

        if (medicalRecord == null) {
            age = null;
        } else {
            age = clock.getAgeFromBirthDate(medicalRecord.getBirthDate());
        }

        return new Person(
                firstName,
                lastName,
                completeAddress.getName(),
                completeAddress.getCity().getName(),
                completeAddress.getCity().getZip(),
                generatePhone(),
                email,
                medicalRecord,
                age);
    }


    /**
     * Return a list of randomly generated Persons without MedicalRecord.
     *
     * @param count           number of Person to generate
     * @param lastName        common name String for all Persons
     * @param completeAddress common Addresses enum value for all Persons
     * @return new Person List.
     */
    public static List<Person> createPersons(final int count,
                                             @Nullable final String lastName,
                                             @Nullable final Addresses completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    firstName,
                    lastName,
                    completeAddress,
                    null));
        }
        return persons;
    }


    /**
     * Return a list of randomly generated Persons with MedicalRecord with adult birthdate.
     *
     * @param count           number of Person to generate
     * @param lastName        common name String for all Persons.
     * @param completeAddress common Addresses enum value for all Persons.
     * @return new Person List.
     */
    public static List<Person> createAdults(final int count,
                                            @Nullable String lastName,
                                            @Nullable Addresses completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;

        if (lastName == null) {
            lastName = generateName();
        }

        if (completeAddress == null) {
            completeAddress = assignAddress();
        }

        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    firstName,
                    lastName,
                    completeAddress,
                    MedicalRecordFactory.createMedicalRecord(
                            firstName,
                            lastName,
                            false)));
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
    public static List<Person> createChildren(final int count,
                                              @Nullable final String lastName,
                                              @Nullable final Addresses completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    firstName,
                    lastName,
                    completeAddress,
                    MedicalRecordFactory.createMedicalRecord(
                            firstName,
                            lastName,
                            true)));
        }
        return persons;
    }
}
