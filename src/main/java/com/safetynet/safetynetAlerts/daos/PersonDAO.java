package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Person;

import java.util.List;

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
    public boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email);

    /**
     * Update specific Person from database.
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
    public boolean update(final String firstName, final String lastName, String address, String city, String zip, String phone, String email);

    /**
     * Delete specific Person from database.
     *
     * @param firstName Person to delete firstName attribute value
     * @param lastName  Person to delete lastName attribute value
     * @return operation success
     */
    public boolean delete(final String firstName, final String lastName);

    /**
     * Get specific Person instance by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @return Person instance
     */
    public Person getFromName(final String firstName, final String lastName);

    /**
     * Get list of Persons by address from database.
     *
     * @param address address value to search
     * @return List of Person instances
     */
    public List<Person> getFromAddress(final String address);
}
