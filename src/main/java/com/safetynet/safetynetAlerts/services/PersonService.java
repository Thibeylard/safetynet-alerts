package com.safetynet.safetynetAlerts.services;

public interface PersonService {

    /**
     * Pass parameters from controllers to DAO to add a new Person into database.
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
     * Pass parameters from controllers to DAO to update specific Person from database.
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
     * Pass parameters from controllers to DAO to delete specific Person from database.
     *
     * @param pFirstName Person to delete firstName attribute value
     * @param pLastName  Person to delete lastName attribute value
     * @return operation success
     */
    public boolean delete(final String pFirstName, final String pLastName);
}
