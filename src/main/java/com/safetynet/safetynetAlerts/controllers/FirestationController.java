package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {

    private FirestationService firestationService;
    @Autowired
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    @PostMapping("/firestation")
    public boolean add(@RequestParam(name = "stationNumber") final int pNumber,
                       @RequestParam(name = "address") final String pAddress) {
        return false;
    }

    @PutMapping("/firestation")
    public boolean update(@RequestParam(name = "address") final String pAddress,
                          @RequestParam(name = "stationNumber") final int pNumber) {
        return false;
    }

    @DeleteMapping(value = "/firestation", params = {"stationNumber"})
    public boolean delete(@RequestParam(name = "stationNumber") final int pNumber) {
        return false;
    }

    @DeleteMapping(value = "/firestation", params = {"address"})
    public boolean delete(@RequestParam(name = "address") final String pAddress) {
        return false;
    }


}
