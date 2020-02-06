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
            HttpStatus responseHTTP = null;
            URLFirestationDTO responseDTO = alertsService.getURLFirestationDTO(number);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<URLChildAlertDTO> childAlert(@RequestParam(name = "address") String address) {
        try {
            HttpStatus responseHTTP = null;
            URLChildAlertDTO responseDTO = alertsService.getURLChildAlertDTO(address);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<URLPhoneAlertDTO> phoneAlert(@RequestParam(name = "firestation") int number) {
        try {
            HttpStatus responseHTTP = null;
            URLPhoneAlertDTO responseDTO = alertsService.getURLPhoneAlertDTO(number);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<URLFireDTO> fire(@RequestParam(name = "address") String address) {
        try {
            HttpStatus responseHTTP = null;
            URLFireDTO responseDTO = alertsService.getURLFireDTO(address);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<URLFloodDTO> flood(@RequestParam(name = "stations") List<Integer> stationNumbers) {
        try {
            HttpStatus responseHTTP = null;
            URLFloodDTO responseDTO = alertsService.getURLFloodDTO(stationNumbers);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<URLPersonInfoDTO> personInfo(@RequestParam(name = "firstName") String firstName,
                                       @RequestParam(name = "lastName") String lastName) {
        try {
            HttpStatus responseHTTP = null;
            URLPersonInfoDTO responseDTO = alertsService.getURLPersonInfoDTO(firstName, lastName);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<URLCommunityEmailDTO> communityEmail(@RequestParam(name = "city") String city) {
        try {
            HttpStatus responseHTTP = null;
            URLCommunityEmailDTO responseDTO = alertsService.getURLCommunityEmailDTO(city);

            if(responseDTO == null){
                responseHTTP = HttpStatus.NO_CONTENT;
            } else {
                responseHTTP = HttpStatus.OK;
            }

            return new ResponseEntity<>(responseDTO,responseHTTP);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
