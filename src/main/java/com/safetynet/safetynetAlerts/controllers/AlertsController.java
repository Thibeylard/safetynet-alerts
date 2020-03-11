package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.services.AlertsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.tinylog.Logger;

import java.io.IOException;
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
            Logger.debug("GET Request on /firestation with stationNumber parameter {}", number);
            URLFirestationDTO responseDTO = alertsService.getURLFirestationDTO(number);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/childAlert")
    public ResponseEntity<URLChildAlertDTO> childAlert(@RequestParam(name = "address") String address) {
        try {
            Logger.debug("GET Request on /childAlert with address parameter {}", address);
            URLChildAlertDTO responseDTO = alertsService.getURLChildAlertDTO(address);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<URLPhoneAlertDTO> phoneAlert(@RequestParam(name = "firestation") int number) {
        try {
            Logger.debug("GET Request on /phoneAlert with firestation parameter {}", number);
            URLPhoneAlertDTO responseDTO = alertsService.getURLPhoneAlertDTO(number);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/fire")
    public ResponseEntity<URLFireDTO> fire(@RequestParam(name = "address") String address) {
        try {
            Logger.debug("GET Request on /fire with address parameter {}", address);
            URLFireDTO responseDTO = alertsService.getURLFireDTO(address);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<URLFloodDTO> flood(@RequestParam(name = "stations") List<Integer> stationNumbers) {
        try {
            Logger.debug("GET Request on /flood with stations parameters {}", stationNumbers);
            URLFloodDTO responseDTO = alertsService.getURLFloodDTO(stationNumbers);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/personInfo")
    public ResponseEntity<URLPersonInfoDTO> personInfo(@RequestParam(name = "firstName") String firstName,
                                                       @RequestParam(name = "lastName") String lastName) {
        try {
            Logger.debug("GET Request on /personInfo with parameters {} and {}", firstName, lastName);
            URLPersonInfoDTO responseDTO = alertsService.getURLPersonInfoDTO(firstName, lastName);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<URLCommunityEmailDTO> communityEmail(@RequestParam(name = "city") String city) {
        try {
            Logger.debug("GET Request on /personInfo with city parameter {}", city);
            URLCommunityEmailDTO responseDTO = alertsService.getURLCommunityEmailDTO(city);

            if (responseDTO == null) {
                Logger.info("GET Request succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
