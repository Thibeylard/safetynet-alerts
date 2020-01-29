package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private MedicalRecordDAO medicalRecordDAO;

    public MedicalRecordServiceImpl(MedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }


    /**
     * @see MedicalRecordService add()
     */
    @Override
    public boolean add(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies) {
        return false;
    }

    /**
     * @see MedicalRecordService update()
     */
    @Override
    public boolean update(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies) {
        return false;
    }

    /**
     * @see MedicalRecordService delete()
     */
    @Override
    public boolean delete(String firstName, String lastName) {
        return false;
    }
}
