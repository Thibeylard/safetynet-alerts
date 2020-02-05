package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.PersonService;
import com.safetynet.safetynetAlerts.services.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<HttpStatus> add(@RequestParam(name = "firstName") final String firstName,
                                          @RequestParam(name = "lastName") final String lastName,
                                          @RequestParam(name = "address") final String address,
                                          @RequestParam(name = "city") final String city,
                                          @RequestParam(name = "zip") final String zip,
                                          @RequestParam(name = "phone") final String phone,
                                          @RequestParam(name = "email") final String email) {
        if (this.personService.add(firstName, lastName, address, city, zip, phone, email)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName,
                                             @RequestParam(name = "address", required = false) String address,
                                             @RequestParam(name = "city", required = false) String city,
                                             @RequestParam(name = "zip", required = false) String zip,
                                             @RequestParam(name = "phone", required = false) String phone,
                                             @RequestParam(name = "email", required = false) String email) {
        MultiValueMap<String, String> optionalParameters = new LinkedMultiValueMap<String, String>();

        if(address != null) {
            optionalParameters.add("address", address);
        }

        if(city != null) {
            optionalParameters.add("city", city);
        }

        if(zip != null) {
            optionalParameters.add("zip", zip);
        }

        if(phone != null) {
            optionalParameters.add("phone", phone);
        }

        if(email != null) {
            optionalParameters.add("email", email);
        }

        if(optionalParameters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.personService.update(firstName, lastName, optionalParameters)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName) {
        if (this.personService.delete(firstName, lastName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
