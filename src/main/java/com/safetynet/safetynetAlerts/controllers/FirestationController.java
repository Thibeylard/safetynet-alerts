package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        if (this.firestationService.add(address, number)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "address") final String address,
                                             @RequestParam(name = "stationNumber") final int number) {
        if (this.firestationService.update(address, number)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "stationNumber") final int number) {
        if (this.firestationService.delete(number)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "address") final String address) {
        if (this.firestationService.delete(address)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
