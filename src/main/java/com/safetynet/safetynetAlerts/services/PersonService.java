package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

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
    boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email) throws IOException, IllegalDataOverrideException;

    /**
     * Pass parameters from controllers to DAO to update specific Person from database.
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
    boolean update(final String firstName,
                   final String lastName,
                   final Optional<String> address,
                   final Optional<String> city,
                   final Optional<String> zip,
                   final Optional<String> phone,
                   final Optional<String> email) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to delete specific Person from database.
     *
     * @param firstName Person to delete firstName attribute value
     * @param lastName  Person to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName, final String lastName) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to get specific Person from database.
     *
     * @param firstName Person to get firstName attribute value
     * @param lastName  Person to get lastName attribute value
     * @return operation success
     */
    Person get(String firstName, String lastName) throws IOException, NoSuchDataException;
}
