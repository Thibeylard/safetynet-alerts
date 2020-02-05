package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        if (this.medicalRecordService.add(firstName,lastName,birthDate,medications,allergies)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "firstName") final String firstName,
                          @RequestParam(name = "lastName") final String lastName,
                          @RequestParam(name = "birthDate", required = false) final String birthDate,
                          @RequestParam(name = "medications", required = false) final List<String> medications,
                          @RequestParam(name = "allergies", required = false) final List<String> allergies) {

        MultiValueMap<String, String> optionalParameters = new LinkedMultiValueMap<String, String>();

        if(birthDate != null) {
            optionalParameters.add("birthDate", birthDate);
        }

        if(medications != null) {
            optionalParameters.addAll("medications", medications);
        }

        if(allergies != null) {
            optionalParameters.addAll("allergies", medications);
        }

        if(optionalParameters.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (this.medicalRecordService.update(firstName, lastName, optionalParameters)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "firstName") final String firstName,
                          @RequestParam(name = "lastName") final String lastName) {
        if (this.medicalRecordService.delete(firstName,lastName)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
