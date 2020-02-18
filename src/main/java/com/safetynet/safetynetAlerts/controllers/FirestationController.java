package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

@RestController
public class FirestationController {

    private FirestationService firestationService;
    @Autowired
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<HttpStatus> add( @RequestParam(name = "address") final String address,
                                           @RequestParam(name = "stationNumber") final int number) {
        Logger.debug("Firestation POST Request with address :" + address + "and station number : " + number);
        if (this.firestationService.add(address, number)) {
            Logger.info("Firestation addition succeed.");
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            Logger.error("Firestation addition failed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "address") final String address,
                                             @RequestParam(name = "stationNumber") final int number) {
        Logger.debug("Firestation PUT Request for address :" + address + "with station number : " + number);
        if (this.firestationService.update(address, number)) {
            Logger.info("Firestation update succeed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            Logger.error("Firestation update failed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "stationNumber") final int number) {
        Logger.debug("Firestation DELETE Request by station number : " + number);
        if (this.firestationService.delete(number)) {
            Logger.info("Firestation deletion succeed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            Logger.error("Firestation deletion failed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "address") final String address) {
        Logger.debug("Firestation DELETE Request by address : " + address);
        if (this.firestationService.delete(address)) {
            Logger.info("Firestation deletion succeed.");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            Logger.error("Firestation deletion failed.");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
