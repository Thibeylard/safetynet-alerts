package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

@RestController
public class MedicalRecordController {

    private MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<String> add(@RequestParam(name = "firstName") final String firstName,
                                      @RequestParam(name = "lastName") final String lastName,
                                      @RequestParam(name = "birthDate") final String birthDate,
                                      @RequestParam(name = "medications") final List<String> medications,
                                      @RequestParam(name = "allergies") final List<String> allergies) {
        Logger.debug("MedicalRecord POST Request with parameters : {}, {}, {}, {}, {}",
                firstName,
                lastName,
                birthDate,
                medications,
                allergies);

        try {
            this.medicalRecordService.add(firstName, lastName, birthDate, medications, allergies);
            return new ResponseEntity<String>("MedicalRecord POST Request succeed", HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<String>("MedicalRecord POST Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalDataOverrideException e) {
            return new ResponseEntity<String>("MedicalRecord POST Request failed : Data could not be created because identifiers already exist in database", HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<String> update(@RequestParam(name = "firstName") final String firstName,
                                         @RequestParam(name = "lastName") final String lastName,
                                         @RequestParam(name = "birthDate", required = false) String birthDate,
                                         @RequestParam(name = "medications", required = false) final List<String> medications,
                                         @RequestParam(name = "allergies", required = false) final List<String> allergies) {

        if (birthDate == null && medications == null && allergies == null) {
            Logger.error("MedicalRecord PUT request error : No parameters to update.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Logger.debug("MedicalRecord PUT Request on {}, {} with parameters : birthdate : {}, medications : {}, allergies : {}",
                firstName,
                lastName,
                birthDate == null ? "not" : birthDate,
                medications == null ? "not" : medications,
                allergies == null ? "not" : allergies);

        try {
            this.medicalRecordService.update(firstName, lastName, birthDate, medications, allergies);
            return new ResponseEntity<String>("MedicalRecord PUT Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("MedicalRecord PUT Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("MedicalRecord PUT Request failed : Data could not be modified because it doesnt exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<String> delete(@RequestParam(name = "firstName") final String firstName,
                                         @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("MedicalRecord DELETE Request on : {} {}", firstName, lastName);

        try {
            this.medicalRecordService.delete(firstName, lastName);
            return new ResponseEntity<String>("MedicalRecord DELETE Request succeed", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("MedicalRecord DELETE Request failed : Database could not be accessed", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<String>("MedicalRecord DELETE Request failed : Data could not be deleted because it doesnt exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> get(@RequestParam(name = "firstName") final String firstName,
                                             @RequestParam(name = "lastName") final String lastName) {
        Logger.debug("MedicalRecord GET Request on : {} {}", firstName, lastName);

        try {
            return new ResponseEntity<MedicalRecord>(this.medicalRecordService.get(firstName, lastName), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<MedicalRecord>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NoSuchDataException e) {
            return new ResponseEntity<MedicalRecord>(HttpStatus.NOT_FOUND);
        }
    }
}
