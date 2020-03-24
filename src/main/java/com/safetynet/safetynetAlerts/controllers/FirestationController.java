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
            Logger.info("Firestation POST Request succeed");
            return new ResponseEntity<String>(HttpStatus.CREATED);
        } catch (IOException e) {
            Logger.error("Firestation POST Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalDataOverrideException e) {
            Logger.error("Firestation POST Request failed : Data could not be created because identifiers already exist in database");
            return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<String> update(@RequestParam(name = "address") final String address,
                                         @RequestParam(name = "stationNumber") final int number) throws Exception {
        Logger.debug("Firestation PUT Request for address : {} with station number : {}", address, number);

        try {
            this.firestationService.update(address, number);
            Logger.info("Firestation PUT Request succeed");
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Firestation PUT Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Firestation PUT Request failed : Data could not be modified because it doesnt exist");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public ResponseEntity<String> delete(@RequestParam(name = "stationNumber") final int number) throws Exception {
        Logger.debug("Firestation DELETE Request by station number : {}", number);

        try {
            this.firestationService.delete(number);
            Logger.info("Firestation DELETE Request succeed");
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Firestation DELETE Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Firestation DELETE Request failed : Data could not be deleted because it doesnt exist");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<String> delete(@RequestParam(name = "address") final String address) throws Exception {
        Logger.debug("Firestation DELETE Request by address : {}", address);
        try {
            this.firestationService.delete(address);
            Logger.info("Firestation DELETE Request succeed");
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Firestation DELETE Request failed : Database could not be accessed");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Firestation DELETE Request failed : Data could not be deleted because it doesnt exist");
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<Firestation> get(@RequestParam(name = "address") final String address) {
        Logger.debug("Firestation GET Request by address : {}", address);

        try {
            Firestation response = this.firestationService.get(address);
            Logger.info("Firestation GET Request succeed");
            return new ResponseEntity<Firestation>(response, HttpStatus.OK);
        } catch (IOException e) {
            Logger.error("Firestation GET Request failed : Database could not be accessed");
            return new ResponseEntity<Firestation>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            Logger.error("Firestation GET Request failed : Data could not be accessed because it doesnt exist");
            return new ResponseEntity<Firestation>(HttpStatus.NOT_FOUND);
        }
    }
}
