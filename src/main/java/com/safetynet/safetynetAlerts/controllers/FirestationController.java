package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.io.IOException;

@RestController
public class FirestationController {

    private FirestationService firestationService;

    @Autowired
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<String> add(@RequestParam(name = "address") final String address,
                                      @RequestParam(name = "stationNumber") final int number) throws Exception {
        Logger.debug("Firestation POST Request with address : {} and station number : {}", address, number);

        try {
            this.firestationService.add(address, number);
            return new ResponseEntity<String>("Firestation POST Request succeed", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<String>("Firestation POST Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalDataOverrideException e) {
            return new ResponseEntity<String>("Firestation POST Request failed : Data could not be created because identifiers already exist in database", HttpStatus.FORBIDDEN);
        }
    }

    //TODO Remove Optionals from parameters
    @PutMapping("/firestation")
    public ResponseEntity<String> update(@RequestParam(name = "address") final String address,
                                         @RequestParam(name = "stationNumber") final int number) throws Exception {
        Logger.debug("Firestation PUT Request for address : {} with station number : {}", address, number);

        try {
            this.firestationService.update(address, number);
            return new ResponseEntity<String>("Firestation PUT Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("Firestation PUT Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("Firestation PUT Request failed : Data could not be modified because it doesnt exist", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public ResponseEntity<String> delete(@RequestParam(name = "stationNumber") final int number) throws Exception {
        Logger.debug("Firestation DELETE Request by station number : {}", number);

        try {
            this.firestationService.delete(number);
            return new ResponseEntity<String>("Firestation DELETE Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("Firestation DELETE Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("Firestation DELETE Request failed : Data could not be deleted because it doesnt exist", HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<String> delete(@RequestParam(name = "address") final String address) throws Exception {
        Logger.debug("Firestation DELETE Request by address : {}", address);
        try {
            this.firestationService.delete(address);
            return new ResponseEntity<String>("Firestation DELETE Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("Firestation DELETE Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("Firestation DELETE Request failed : Data could not be deleted because it doesnt exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<Firestation> get(@RequestParam(name = "address") final String address) {
        Logger.debug("Firestation GET Request by address : {}", address);

        try {
            return new ResponseEntity<Firestation>(this.firestationService.get(address), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<Firestation>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<Firestation>(HttpStatus.NOT_FOUND);
        }
    }
}
