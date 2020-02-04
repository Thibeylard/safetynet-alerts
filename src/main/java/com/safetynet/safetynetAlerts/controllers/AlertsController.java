package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.services.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<URLFirestationDTO> firestation(@RequestParam(name = "stationNumber") int number) {
        try {
            URLFirestationDTO responseDTO = alertsService.getURLFirestationDTO(number);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<URLChildAlertDTO> childAlert(@RequestParam(name = "address") String address) {
        try {
            URLChildAlertDTO responseDTO = alertsService.getURLChildAlertDTO(address);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<URLPhoneAlertDTO> phoneAlert(@RequestParam(name = "firestation") int number) {
        try {
            URLPhoneAlertDTO responseDTO = alertsService.getURLPhoneAlertDTO(number);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<URLFireDTO> fire(@RequestParam(name = "address") String address) {
        try {
            URLFireDTO responseDTO = alertsService.getURLFireDTO(address);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<URLFloodDTO> flood(@RequestParam(name = "stations") List<Integer> stationNumbers) {
        try {
            URLFloodDTO responseDTO = alertsService.getURLFloodDTO(stationNumbers);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<URLPersonInfoDTO> personInfo(@RequestParam(name = "firstName") String firstName,
                                       @RequestParam(name = "lastName") String lastName) {
        try {
            URLPersonInfoDTO responseDTO = alertsService.getURLPersonInfoDTO(firstName, lastName);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<URLCommunityEmailDTO> communityEmail(@RequestParam(name = "city") String city) {
        try {
            URLCommunityEmailDTO responseDTO = alertsService.getURLCommunityEmailDTO(city);
            return new ResponseEntity<>(responseDTO,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
