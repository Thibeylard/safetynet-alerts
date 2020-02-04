package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.services.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
public class AlertsController {

    private AlertsService alertsService;

    @Autowired
    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<URLFirestationDTO> firestation(@RequestParam(name = "stationNumber") int pNumber) {
        return null;
    }

    @GetMapping("/childAlert")
    public ResponseEntity<URLChildAlertDTO> childAlert(@RequestParam(name = "address") String pAddress) {
        return null;
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<URLPhoneAlertDTO> phoneAlert(@RequestParam(name = "firestation") int pNumber) {
        return null;
    }

    @GetMapping("/fire")
    public ResponseEntity<URLFireDTO> fire(@RequestParam(name = "address") String pAddress) {
        return null;
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<URLFloodDTO> flood(@RequestParam(name = "stations") List<Integer> stationNumbers) {
        return null;
    }

    @GetMapping("/personInfo")
    public ResponseEntity<URLPersonInfoDTO> personInfo(@RequestParam(name = "firstName") String pFirstName,
                                       @RequestParam(name = "lastName") String pLastName) {
        return null;
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<URLCommunityEmailDTO> communityEmail(@RequestParam(name = "city") String pCity) {
        return null;
    }
}
