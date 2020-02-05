package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.PersonDAO;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonDAO personDAO;

    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    /**
     * @see PersonService add()
     */
    @Override
    public boolean add(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        return false;
    }

    /**
     * @see PersonService update()
     */
    @Override
    public boolean update(String firstName, String lastName, MultiValueMap<String, String> optionalParams) {
        return false;
    }

    /**
     * @see PersonService delete()
     */
    @Override
    public boolean delete(String firstName, String lastName) {
        return false;
    }
}
