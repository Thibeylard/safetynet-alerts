package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.util.List;
import java.util.Optional;

@RestController
public class MedicalRecordController {

    private MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> add(@RequestParam(name = "firstName") final String firstName,
                                          @RequestParam(name = "lastName") final String lastName,
                                          @RequestParam(name = "birthDate") final String birthDate,
                                          @RequestParam(name = "medications") final List<String> medications,
                                          @RequestParam(name = "allergies") final List<String> allergies) {
        Logger.debug("MedicalRecord POST Request with parameters : "
                + firstName + ", "
                + lastName + ", "
                + birthDate + ", "
                + medications + ", "
                + allergies);
        if (this.medicalRecordService.add(firstName, lastName, birthDate, medications, allergies)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName,
                                             @RequestParam(name = "birthDate", required = false) final Optional<String> birthDate,
                                             @RequestParam(name = "medications", required = false) final Optional<List<String>> medications,
                                             @RequestParam(name = "allergies", required = false) final Optional<List<String>> allergies) {

        if (birthDate.isEmpty() && medications.isEmpty() && allergies.isEmpty()) {
            Logger.error("MedicalRecord PUT request error : No parameters to update.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Logger.debug("MedicalRecord PUT Request on "
                + firstName + ", "
                + lastName
                + "with parameters : "
                + (birthDate.isPresent() ? birthDate  + ", " : "no birthdate, ")
                + (medications.isPresent() ? medications + ", " : "no medications, ")
                + (allergies.isPresent() ? allergies : "no allergies"));

        if (this.medicalRecordService.update(firstName, lastName, birthDate, medications, allergies)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("MedicalRecord DELETE Request on : " + firstName + " " + lastName);
        if (this.medicalRecordService.delete(firstName, lastName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
