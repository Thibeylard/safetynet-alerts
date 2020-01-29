package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.PersonService;
import com.safetynet.safetynetAlerts.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public boolean add(@RequestParam(name = "firstName") String pFirstName,
                       @RequestParam(name = "lastName") String pLastName,
                       @RequestParam(name = "address") String pAddress,
                       @RequestParam(name = "city") String pCity,
                       @RequestParam(name = "zip") String pZip,
                       @RequestParam(name = "phone") String pPhone,
                       @RequestParam(name = "email") String pEmail) {
        return false;
    }

    @PutMapping("/person")
    public boolean update(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName,
                          @RequestParam(name = "address", required = false) String pAddress,
                          @RequestParam(name = "city", required = false) String pCity,
                          @RequestParam(name = "zip", required = false) String pZip,
                          @RequestParam(name = "phone", required = false) String pPhone,
                          @RequestParam(name = "email", required = false) String pEmail) {
        return false;
    }

    @DeleteMapping("/person")
    public boolean delete(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName) {
        return false;
    }
}
