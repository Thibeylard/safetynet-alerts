package com.safetynet.safetynetAlerts.controllers;

import com.safetynet.safetynetAlerts.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public boolean add(@RequestParam(name = "firstName") String pFirstName,
                       @RequestParam(name = "lastName") String pLastName,
                       @RequestParam(name = "birthDate") final String pBirthDate,
                       @RequestParam(name = "medications") final List<String> pMedications,
                       @RequestParam(name = "allergies") final List<String> pAllergies) {
        return false;
    }

    @PutMapping("/medicalRecord")
    public boolean update(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName,
                          @RequestParam(name = "birthDate", required = false) final String pBirthDate,
                          @RequestParam(name = "medications", required = false) final List<String> pMedications,
                          @RequestParam(name = "allergies", required = false) final List<String> pAllergies) {
        return false;
    }

    @DeleteMapping("/medicalRecord")
    public boolean delete(@RequestParam(name = "firstName") String pFirstName,
                          @RequestParam(name = "lastName") String pLastName) {
        return false;
    }

}
