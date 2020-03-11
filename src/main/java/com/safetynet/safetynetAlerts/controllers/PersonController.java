package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Person;
import com.safetynet.safetynetAlerts.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.Optional;

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<String> add(@RequestParam(name = "firstName") final String firstName,
                                      @RequestParam(name = "lastName") final String lastName,
                                      @RequestParam(name = "address") final String address,
                                      @RequestParam(name = "city") final String city,
                                      @RequestParam(name = "zip") final String zip,
                                      @RequestParam(name = "phone") final String phone,
                                      @RequestParam(name = "email") final String email) {

        Logger.debug("Person POST Request with parameters : {}, {}, {}, {}, {}, {}, {}.",
                firstName,
                lastName,
                address,
                city,
                zip,
                phone,
                email);

        try {
            this.personService.add(firstName, lastName, address, city, zip, phone, email);
            Logger.info("Person POST Request succeed");
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } catch (IOException e) {
            Logger.error("Person POST Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalDataOverrideException e) {
            Logger.error("Person POST Request failed : Data could not be created because identifiers already exist in database");
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    //TODO Remove Optionals from parameters
    @PutMapping("/person")
    public ResponseEntity<String> update(@RequestParam(name = "firstName") final String firstName,
                                         @RequestParam(name = "lastName") final String lastName,
                                         @RequestParam(name = "address", required = false) final Optional<String> address,
                                         @RequestParam(name = "city", required = false) final Optional<String> city,
                                         @RequestParam(name = "zip", required = false) final Optional<String> zip,
                                         @RequestParam(name = "phone", required = false) final Optional<String> phone,
                                         @RequestParam(name = "email", required = false) final Optional<String> email) {

        if (address.isEmpty() && city.isEmpty() && zip.isEmpty() & phone.isEmpty() && email.isEmpty()) {
            Logger.error("Person PUT request error : No parameters to update.");
            return new ResponseEntity<>("At least one optional parameter is needed.", HttpStatus.BAD_REQUEST);
        }

        Logger.debug("Person PUT Request on {}, {} with parameters : {}, {}, {}, {}, {}.",
                firstName,
                lastName,
                address.orElse("no address"),
                city.orElse("no city"),
                zip.orElse("no zip"),
                phone.orElse("no phone"),
                email.orElse("no email"));

        try {
            this.personService.update(firstName, lastName, address, city, zip, phone, email);
            return new ResponseEntity<String>("Person PUT Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("Person PUT Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("Person PUT Request failed : Data could not be modified because it doesnt exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/person")
    public ResponseEntity<String> delete(@RequestParam(name = "firstName") final String firstName,
                                         @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("Person DELETE Request on : " + firstName + " " + lastName);

        try {
            this.personService.delete(firstName, lastName);
            Logger.info("Person DELETE Request succeed");
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Person DELETE Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Person DELETE Request failed : Data could not be deleted because it doesnt exist");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/person")
    public ResponseEntity<Person> get(@RequestParam(name = "firstName") final String firstName,
                                      @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("Person GET Request on : {} {}", firstName, lastName);

        try {
            return new ResponseEntity<Person>(this.personService.get(firstName, lastName), HttpStatus.OK);
        } catch (NoSuchDataException | NoMedicalRecordException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
