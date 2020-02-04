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
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean add(final String firstName, 
                       final String lastName, 
                       final String address, 
                       final String city, 
                       final String zip, 
                       final String phone, 
                       final String email) throws Exception;

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
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean update(final String firstName, 
                          final String lastName, 
                          final String address, 
                          final String city, 
                          final String zip, 
                          final String phone, 
                          final String email) throws Exception;

    /**
     * Delete specific Person from database.
     *
     * @param firstName Person to delete firstName attribute value
     * @param lastName  Person to delete lastName attribute value
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean delete(final String firstName, 
                          final String lastName) throws Exception;

    /**
     * Get specific Person instance by whole name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @throws Exception for data access failure
     * @return Person instance
     */
    public Person getFromName(final String firstName, 
                              final String lastName) throws Exception;

    /**
     * Get specific Person instances by lastName from database.
     *
     * @param lastName  lastName value to search
     * @throws Exception for data access failure
     * @return Person instance
     */
    public List<Person> getFromName(final String lastName) throws Exception;

    /**
     * Get list of Persons by address from database.
     *
     * @param address address value to search
     * @throws Exception for data access failure
     * @return List of Person instances
     */
    public List<Person> getFromAddress(final String address) throws Exception;

    /**
     * Get list of Persons by city from database.
     *
     * @param city address value to search
     * @throws Exception for data access failure
     * @return List of Person instances
     */
    public List<Person> getCommunity(final String city) throws Exception;
}
