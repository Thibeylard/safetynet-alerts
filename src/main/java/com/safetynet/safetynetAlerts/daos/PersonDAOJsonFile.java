package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Person;
import com.safetynet.safetynetAlerts.services.ClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonDAOJsonFile implements PersonDAO {

    ClockService clock;
    /**
     * JsonFileDatabase singleton instance.
     */
    private final JsonFileDatabase jsonFileDatabase;

    /**
     * Requires JsonFileDatabase to operate on JSON data file.
     *
     * @param jsonFileDatabase instance to initialize this.jsonFileDatabase
     */
    @Autowired
    public PersonDAOJsonFile(final JsonFileDatabase jsonFileDatabase, ClockService clock) {
        this.jsonFileDatabase = jsonFileDatabase;
        this.clock = clock;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public boolean add(final String firstName,
                       final String lastName,
                       final String address,
                       final String city,
                       final String zip,
                       final String phone,
                       final String email) throws IOException, IllegalDataOverrideException {
        Logger.debug("Person DAO pass add request to JsonFileDatabase");
        return jsonFileDatabase.addPerson(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonDAO
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final String address,
                          final String city,
                          final String zip,
                          final String phone,
                          final String email) throws IOException, NoSuchDataException {
        Logger.debug("Person DAO pass update request to JsonFileDatabase");
        return jsonFileDatabase.updatePerson(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonDAO
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) throws IOException, NoSuchDataException {
        Logger.debug("Person DAO pass delete request to JsonFileDatabase");
        return jsonFileDatabase.deletePerson(firstName, lastName);
    }

    /**
     * @see PersonDAO
     */
    @Override
    public Person getFromName(final String firstName,
                              final String lastName,
                              final boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {

        return this.jsonFileDatabase.getPerson(firstName, lastName, withMedicalRecord);
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getFromAddress(final String address,
                                       boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        return this.jsonFileDatabase.getPersonFromAddress(address, withMedicalRecord);
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getFromAddress(List<String> addresses,
                                       boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        List<Person> persons = new ArrayList<>();
        for (String address : addresses) {
            persons.addAll(this.jsonFileDatabase.getPersonFromAddress(address, withMedicalRecord));
        }
        return persons;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getCommunity(final String city,
                                     boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        return this.jsonFileDatabase.getPersonFromCity(city, withMedicalRecord);
    }
}
