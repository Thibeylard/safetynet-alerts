package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    public ResponseEntity<HttpStatus> add(@RequestParam(name = "stationNumber") final int pNumber,
                          @RequestParam(name = "address") final String pAddress) {
        return null;
    }

    @PutMapping("/firestation")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "address") final String pAddress,
                          @RequestParam(name = "stationNumber") final int pNumber) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "stationNumber") final int pNumber) {
        return null;
    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "address") final String pAddress) {
        return null;
    }


}
