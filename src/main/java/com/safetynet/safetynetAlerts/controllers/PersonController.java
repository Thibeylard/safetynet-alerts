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

@RestController
public class PersonController {

    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Add Person to database (identifier is firstName lastName couple).
     *
     * @param firstName value to set to eponym attribute
     * @param lastName  value to set to eponym attribute
     * @param address   value to set to eponym attribute
     * @param city      value to set to eponym attribute
     * @param zip       value to set to eponym attribute
     * @param phone     value to set to eponym attribute
     * @param email     value to set to eponym attribute
     * @return ResponseEntity of String
     */
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

    /**
     * Modify Person address, city, zip, phone, and/or email in database.
     *
     * @param firstName firstName to search for
     * @param lastName  lastName to search for
     * @param address   address new value (Nullable)
     * @param city      city new value (Nullable)
     * @param zip       zip new value (Nullable)
     * @param phone     phone new value (Nullable)
     * @param email     email new value (Nullable)
     * @return ResponseEntity of String
     */
    @PutMapping("/person")
    public ResponseEntity<String> update(@RequestParam(name = "firstName") final String firstName,
                                         @RequestParam(name = "lastName") final String lastName,
                                         @RequestParam(name = "address", required = false) final String address,
                                         @RequestParam(name = "city", required = false) final String city,
                                         @RequestParam(name = "zip", required = false) final String zip,
                                         @RequestParam(name = "phone", required = false) final String phone,
                                         @RequestParam(name = "email", required = false) final String email) {

        if (address == null && city == null && zip == null & phone == null && email == null) {
            Logger.error("Person PUT request error : No parameters to update.");
            return new ResponseEntity<>("At least one optional parameter is needed.", HttpStatus.BAD_REQUEST);
        }

        Logger.debug("Person PUT Request on {}, {} with parameters : address : {}, city : {}, zip : {}, phone : {}, email : {}.",
                firstName,
                lastName,
                address == null ? "not" : address,
                city == null ? "not" : city,
                zip == null ? "not" : zip,
                phone == null ? "not" : phone,
                email == null ? "not" : email);

        try {
            this.personService.update(firstName, lastName, address, city, zip, phone, email);
            Logger.info("Person PUT Request succeed");
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Person PUT Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Person PUT Request failed : Data could not be modified because it doesnt exist");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete Person in database.
     *
     * @param firstName firstName to search for
     * @param lastName  lastName to search for
     * @return ResponseEntity of String
     */
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

    /**
     * Get Person from database.
     *
     * @param firstName firstName to search for
     * @param lastName  lastName to search for
     * @return ResponseEntity of Person
     */
    @GetMapping("/person")
    public ResponseEntity<Person> get(@RequestParam(name = "firstName") final String firstName,
                                      @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("Person GET Request on : {} {}", firstName, lastName);

        try {
            Person response = this.personService.get(firstName, lastName);
            Logger.info("Person GET Request succeed");
            return new ResponseEntity<Person>(response, HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Person GET Request failed : Database could not be accessed");
            return new ResponseEntity<Person>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException | NoMedicalRecordException e) {
            Logger.error("Person GET Request failed : Data could not be accessed because it doesnt exist");
            return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
        }
    }

}
