package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonDAOJsonFile implements PersonDAO {

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
    public PersonDAOJsonFile(final JsonFileDatabase jsonFileDatabase) {
        this.jsonFileDatabase = jsonFileDatabase;
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
                          final Optional<String> address,
                          final Optional<String> city,
                          final Optional<String> zip,
                          final Optional<String> phone,
                          final Optional<String> email) throws IOException, NoSuchDataException {
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

    //TODO Enable asking for person related medical record  when ask for person.

    /**
     * @see PersonDAO
     */
    @Override
    public Person getFromName(final String firstName,
                              final String lastName) throws IOException, NoSuchDataException {
        return null;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getFromName(final String lastName) throws IOException, NoSuchDataException {
        return null;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getFromAddress(final String address) throws IOException, NoSuchDataException {
        return null;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getFromAddress(List<String> addresses) throws IOException, NoSuchDataException {
        return null;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public List<Person> getCommunity(final String city) throws IOException, NoSuchDataException {
        return null;
    }
}
