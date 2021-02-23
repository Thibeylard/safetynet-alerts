package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private MedicalRecordDAO medicalRecordDAO;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }


    /**
     * @see MedicalRecordService
     */
    @Override
    public boolean add(final String firstName,
                       final String lastName,
                       final String birthDate,
                       final List<String> medications,
                       final List<String> allergies) throws IOException, IllegalDataOverrideException {
        Logger.debug("MedicalRecord Service pass add request to DAO");
        return this.medicalRecordDAO.add(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordService
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final String birthDate,
                          final List<String> medications,
                          final List<String> allergies) throws IOException, NoSuchDataException {
        Logger.debug("MedicalRecord Service pass update request to DAO");
        return this.medicalRecordDAO.update(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordService
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) throws IOException, NoSuchDataException {
        Logger.debug("MedicalRecord Service pass delete request to DAO");
        return this.medicalRecordDAO.delete(firstName, lastName);
    }

    /**
     * @see MedicalRecordService
     */
    @Override
    public MedicalRecord get(String firstName, String lastName) throws IOException, NoSuchDataException {
        return medicalRecordDAO.get(firstName, lastName);
    }
}
