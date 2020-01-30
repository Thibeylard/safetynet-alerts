package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<HttpStatus> add(@RequestParam(name = "firstName") String pFirstName,
                                          @RequestParam(name = "lastName") String pLastName,
                                          @RequestParam(name = "birthDate") final String pBirthDate,
                                          @RequestParam(name = "medications") final List<String> pMedications,
                                          @RequestParam(name = "allergies") final List<String> pAllergies) {
        return null;
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> update(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName,
                          @RequestParam(name = "birthDate", required = false) final String pBirthDate,
                          @RequestParam(name = "medications", required = false) final List<String> pMedications,
                          @RequestParam(name = "allergies", required = false) final List<String> pAllergies) {
        return null;
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> delete(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName) {
        return null;
    }

}
