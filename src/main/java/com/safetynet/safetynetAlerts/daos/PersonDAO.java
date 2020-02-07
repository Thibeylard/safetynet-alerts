package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface PersonDAO {

    /**
     * Add a new Person into database.
     *
     * @param firstName value to set for firstName attribute
     * @param lastName  value to set for lastName attribute
     * @param address   value to set for address attribute
     * @param city      value to set for city attribute
     * @param zip       value to set for zip attribute
     * @param phone     value to set for phone attribute
     * @param email     value to set for email attribute
     * @return operation success
     */
    public boolean add(final String firstName,
                       final String lastName,
                       final String address,
                       final String city,
                       final String zip,
                       final String phone,
                       final String email);

    /**
     * Update specific Person from database.
     *
     * @param firstName value to set for firstName attribute
     * @param lastName  value to set for lastName attribute
     * @param address   optional value to set for address attribute
     * @param city      optional value to set for city attribute
     * @param zip       optional value to set for zip attribute
     * @param phone     optional value to set for phone attribute
     * @param email     optional value to set for email attribute
     * @return operation success
     */
    public boolean update(final String firstName,
                          final String lastName,
                          final Optional<String> address,
                          final Optional<String> city,
                          final Optional<String> zip,
                          final Optional<String> phone,
                          final Optional<String> email);

    /**
     * Delete specific Person from database.
     *
     * @param firstName Person to delete firstName attribute value
     * @param lastName  Person to delete lastName attribute value
     * @return operation success
     */
    public boolean delete(final String firstName,
                          final String lastName);

    /**
     * Get specific Person instance by whole name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @return Person instance
     * @throws Exception for data access failure
     */
    public Person getFromName(final String firstName,
                              final String lastName) throws Exception;

    /**
     * Get specific Person instances by lastName from database.
     *
     * @param lastName lastName value to search
     * @return Person instance
     * @throws Exception for data access failure
     */
    public List<Person> getFromName(final String lastName) throws Exception;

    /**
     * Get list of Persons by address from database.
     *
     * @param address address value to search
     * @return List of Person instances
     * @throws Exception for data access failure
     */
    public List<Person> getFromAddress(final String address) throws Exception;

    /**
     * Get list of Persons by city from database.
     *
     * @param city address value to search
     * @return List of Person instances
     * @throws Exception for data access failure
     */
    public List<Person> getCommunity(final String city) throws Exception;
}
