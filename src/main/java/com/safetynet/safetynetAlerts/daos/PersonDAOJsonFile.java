package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.util.List;

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
                       final String email) {
        return false;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final MultiValueMap<String, String> optionalParams) {
        return false;
    }

    /**
     * @see PersonDAO
     * @param firstName
     * @param lastName
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) {
        return false;
    }

    /**
     * @see PersonDAO
     * @param firstName
     * @param lastName
     */
    @Override
    public Person getFromName(final String firstName,
                              final String lastName) throws Exception {
        return null;
    }

    /**
     * Get specific Person instances by lastName from database.
     *
     * @param lastName lastName value to search
     * @return Person instance
     */
    @Override
    public List<Person> getFromName(final String lastName) throws Exception {
        return null;
    }

    /**
     * @see PersonDAO
     * @param address
     */
    @Override
    public List<Person> getFromAddress(final String address) throws Exception {
        return null;
    }

    /**
     * Get list of Persons by city from database.
     *
     * @param city address value to search
     * @return List of Person instances
     */
    @Override
    public List<Person> getCommunity(final String city) throws Exception {
        return null;
    }
}
