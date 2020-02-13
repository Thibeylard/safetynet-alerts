package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.tinylog.Logger;

import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    @Autowired
    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    /**
     * @see PersonService add()
     */
    @Override
    public boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        Logger.debug("Person Service pass add request to DAO");
        return this.personDAO.add(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonService update()
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final Optional<String> address,
                          final Optional<String> city,
                          final Optional<String> zip,
                          final Optional<String> phone,
                          final Optional<String> email) {
        Logger.debug("Person Service pass update request to DAO");
        return this.personDAO.update(firstName, lastName, address, city, zip, phone, email);
    }

    /**
     * @see PersonService delete()
     */
    @Override
    public boolean delete(String firstName, String lastName) {
        Logger.debug("Person Service pass delete request to DAO");
        return this.personDAO.delete(firstName, lastName);
    }
}
