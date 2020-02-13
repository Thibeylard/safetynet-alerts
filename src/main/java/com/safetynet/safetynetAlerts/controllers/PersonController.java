package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.Optional;

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

        Logger.debug("Person POST Request with parameters : "
                + firstName + ", "
                + lastName + ", "
                + address + ", "
                + city + ", "
                + zip + ", "
                + phone + ", "
                + email);

        if (this.personService.add(firstName, lastName, address, city, zip, phone, email)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName,
                                             @RequestParam(name = "address", required = false) final Optional<String> address,
                                             @RequestParam(name = "city", required = false) final Optional<String> city,
                                             @RequestParam(name = "zip", required = false) final Optional<String> zip,
                                             @RequestParam(name = "phone", required = false) final Optional<String> phone,
                                             @RequestParam(name = "email", required = false) final Optional<String> email) {

        if (address.isEmpty() && city.isEmpty() && zip.isEmpty() & phone.isEmpty() && email.isEmpty()) {
            Logger.error("Person PUT request error : No parameters to update.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Logger.debug("Person PUT Request on "
                + firstName + ", "
                + lastName
                + "with parameters : "
                + (address.isPresent() ? address  + ", " : "no address, ")
                + (city.isPresent() ? city + ", " : "no city, ")
                + (zip.isPresent() ? zip + ", " : "no zip, ")
                + (phone.isPresent() ? phone + ", " : "no phone, ")
                + (email.isPresent() ? email : "no email"));

        if (this.personService.update(firstName, lastName, address, city, zip, phone, email)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("Person DELETE Request on : " + firstName + " " + lastName);
        if (this.personService.delete(firstName, lastName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
