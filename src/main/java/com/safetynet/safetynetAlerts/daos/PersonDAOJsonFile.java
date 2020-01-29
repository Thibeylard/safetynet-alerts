package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        return false;
    }

    /**
     * @see PersonDAO
     */
    @Override
    public boolean update(final String firstName, final String lastName, String address, String city, String zip, String phone, String email) {
        return false;
    }

    /**
     * @see PersonDAO
     * @param firstName
     * @param lastName
     */
    @Override
    public boolean delete(final String firstName, final String lastName) {
        return false;
    }

    /**
     * @see PersonDAO
     * @param firstName
     * @param lastName
     */
    @Override
    public Person getFromName(final String firstName, final String lastName) {
        return null;
    }

    /**
     * @see PersonDAO
     * @param address
     */
    @Override
    public List<Person> getFromAddress(final String address) {
        return null;
    }
}
