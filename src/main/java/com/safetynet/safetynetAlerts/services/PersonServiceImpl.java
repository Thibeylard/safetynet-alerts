package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.PersonDAO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    /**
     * @see PersonService
     */
    @Override
    public boolean add(final String firstName,
                       final String lastName,
                       final String address,
                       final String city,
                       final String zip,
                       final String phone,
                       final String email) throws IOException, IllegalDataOverrideException {
        Logger.debug("Person Service pass add request to DAO");
        return this.personDAO.add(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonService
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final Optional<String> address,
                          final Optional<String> city,
                          final Optional<String> zip,
                          final Optional<String> phone,
                          final Optional<String> email) throws IOException, NoSuchDataException {
        Logger.debug("Person Service pass update request to DAO");
        return this.personDAO.update(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonService
     */
    @Override
    public boolean delete(String firstName, String lastName) throws IOException, NoSuchDataException {
        Logger.debug("Person Service pass delete request to DAO");
        return this.personDAO.delete(firstName, lastName);
    }

    /**
     * @see PersonService
     */
    @Override
    public Person get(String firstName, String lastName) throws IOException, NoSuchDataException {
        return null;
    }
}
