package com.safetynet.safetynetAlerts.services;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
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
    boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email);

    /**
     * Pass parameters from controllers to DAO to update specific Person from database.
     *
     * @param firstName value to set for firstName attribute
     * @param lastName  value to set for lastName attribute
     * @param optionalParams values to set for Person (must contains at least one key)
     * @return operation success
     */
    boolean update(final String firstName, final String lastName, final MultiValueMap<String,String> optionalParams);

    /**
     * Pass parameters from controllers to DAO to delete specific Person from database.
     *
     * @param firstName Person to delete firstName attribute value
     * @param lastName  Person to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName, final String lastName);
}
