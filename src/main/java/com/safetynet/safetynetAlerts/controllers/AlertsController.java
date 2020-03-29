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


    /**
     * Ask for URLFirestationDTO composed of data depending on sent stationNumber.
     *
     * @param number Firestation stationNumber to search for.
     * @return ResponseEntity of URLFirestationDTO or null value
     * @see URLFirestationDTO
     */
    @GetMapping("/firestation")
    public ResponseEntity<URLFirestationDTO> firestation(@RequestParam(name = "stationNumber") int number) {
        try {
            Logger.debug("GET Request on /firestation with stationNumber parameter {}", number);
            URLFirestationDTO responseDTO = alertsService.getURLFirestationDTO(number);

            if (responseDTO == null) {
                Logger.info("GET Request on /firestation succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /firestation succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /firestation failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request on /firestation failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ask for URLChildAlertDTO composed of data depending on sent address.
     *
     * @param address Person address to search for.
     * @return ResponseEntity of URLChildAlertDTO or null value
     * @see URLChildAlertDTO
     */
    @GetMapping("/childAlert")
    public ResponseEntity<URLChildAlertDTO> childAlert(@RequestParam(name = "address") String address) {
        try {
            Logger.debug("GET Request on /childAlert with address parameter {}", address);
            URLChildAlertDTO responseDTO = alertsService.getURLChildAlertDTO(address);

            if (responseDTO == null) {
                Logger.info("GET Request on /childAlert succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /childAlert succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /childAlert failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request on /childAlert failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ask for URLPhoneAlertDTO composed of data depending on sent firestation.
     *
     * @param number Firestation stationNumber to search for.
     * @return ResponseEntity of URLPhoneAlertDTO or null value
     * @see URLPhoneAlertDTO
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<URLPhoneAlertDTO> phoneAlert(@RequestParam(name = "firestation") int number) {
        try {
            Logger.debug("GET Request on /phoneAlert with firestation parameter {}", number);
            URLPhoneAlertDTO responseDTO = alertsService.getURLPhoneAlertDTO(number);

            if (responseDTO == null) {
                Logger.info("GET Request on /phoneAlert succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /phoneAlert succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request failed on /phoneAlert : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Ask for URLFireDTO composed of data depending on sent address.
     *
     * @param address Persons and Firestation address to search for.
     * @return ResponseEntity of URLFireDTO or null value
     * @see URLFireDTO
     */
    @GetMapping("/fire")
    public ResponseEntity<URLFireDTO> fire(@RequestParam(name = "address") String address) {
        try {
            Logger.debug("GET Request on /fire with address parameter {}", address);
            URLFireDTO responseDTO = alertsService.getURLFireDTO(address);

            if (responseDTO == null) {
                Logger.info("GET Request on /fire succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /fire succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /fire failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request on /fire failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ask for URLFloodDTO composed of data depending on sent stations.
     *
     * @param stationNumbers Firestations stationNumber to search for.
     * @return ResponseEntity of URLFloodDTO or null value
     * @see URLFloodDTO
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<URLFloodDTO> flood(@RequestParam(name = "stations") List<Integer> stationNumbers) {
        try {
            Logger.debug("GET Request on /flood with stations parameters {}", stationNumbers);
            URLFloodDTO responseDTO = alertsService.getURLFloodDTO(stationNumbers);

            if (responseDTO == null) {
                Logger.info("GET Request on /flood succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /flood succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /flood failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request on /flood failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ask for URLPersonInfoDTO composed of data depending on sent firstName and lastName.
     *
     * @param firstName Person firstName to search for.
     * @param lastName  Person lastName to search for.
     * @return ResponseEntity of URLPersonInfoDTO or null value
     * @see URLPersonInfoDTO
     */
    @GetMapping("/personInfo")
    public ResponseEntity<URLPersonInfoDTO> personInfo(@RequestParam(name = "firstName") String firstName,
                                                       @RequestParam(name = "lastName") String lastName) {
        try {
            Logger.debug("GET Request on /personInfo with parameters {} and {}", firstName, lastName);
            URLPersonInfoDTO responseDTO = alertsService.getURLPersonInfoDTO(firstName, lastName);

            if (responseDTO == null) {
                Logger.info("GET Request on /personInfo succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /personInfo succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /personInfo  failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoMedicalRecordException e) {
            Logger.error("GET Request on /personInfo  failed : No MedicalRecord present for Person {} {} but necessary", e.getPersonFirstName(), e.getPersonLastName());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Ask for URLCommunityEmailDTO composed of data depending on sent city.
     *
     * @param city Persons city to search for.
     * @return ResponseEntity of URLCommunityEmailDTO or null value
     * @see URLCommunityEmailDTO
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<URLCommunityEmailDTO> communityEmail(@RequestParam(name = "city") String city) {
        try {
            Logger.debug("GET Request on /communityEmail with city parameter {}", city);
            URLCommunityEmailDTO responseDTO = alertsService.getURLCommunityEmailDTO(city);

            if (responseDTO == null) {
                Logger.info("GET Request on /communityEmail succeed but there was nothing to return.");
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            } else {
                Logger.info("GET Request on /communityEmail succeed.");
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }

        } catch (IOException e) {
            Logger.error("GET Request on /communityEmail failed : database could not be read.");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
