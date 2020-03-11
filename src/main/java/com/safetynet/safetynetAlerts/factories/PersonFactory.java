package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import com.safetynet.safetynetAlerts.services.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return createPerson(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
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
    public static Person createPerson(Optional<String> firstName,
                                      Optional<String> lastName,
                                      Optional<Addresses> completeAddress,
                                      Optional<MedicalRecord> medicalRecord) {

        String firstNameStr = firstName.orElse(generateName());
        String lastNameStr = lastName.orElse(generateName());
        String email = firstNameStr + "." + lastNameStr + "@email.com";
        Integer age;

        try {
            age = clock.getAgeFromBirthDate(medicalRecord.orElseThrow(NullPointerException::new).getBirthDate());
        } catch (NullPointerException e) {
            age = null;
        }

        return new Person(
                firstNameStr,
                lastNameStr,
                completeAddress.orElse(assignAddress()).getName(),
                completeAddress.orElse(assignAddress()).getCity().getName(),
                completeAddress.orElse(assignAddress()).getCity().getZip(),
                generatePhone(),
                email,
                medicalRecord.orElse(null),
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
                                             final Optional<String> lastName,
                                             final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    Optional.of(firstName),
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
    public static List<Person> createAdults(final int count,
                                            final Optional<String> lastName,
                                            final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    Optional.of(firstName),
                    lastName,
                    completeAddress,
                    Optional.of(MedicalRecordFactory.createMedicalRecord(
                            firstName,
                            lastName.orElse(null),
                            false))));
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
                                              final Optional<String> lastName,
                                              final Optional<Addresses> completeAddress) {
        List<Person> persons = new ArrayList<>();
        String firstName;
        for (int i = 0; i < count; i++) {
            firstName = generateName();
            persons.add(createPerson(
                    Optional.of(firstName),
                    lastName,
                    completeAddress,
                    Optional.of(MedicalRecordFactory.createMedicalRecord(
                            firstName,
                            lastName.orElse(null),
                            true))));
        }
        return persons;
    }
}
