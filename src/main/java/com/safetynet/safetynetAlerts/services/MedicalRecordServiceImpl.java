package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private MedicalRecordDAO medicalRecordDAO;

    @Autowired
    public MedicalRecordServiceImpl(MedicalRecordDAO medicalRecordDAO) {
        this.medicalRecordDAO = medicalRecordDAO;
    }


    /**
     * @see MedicalRecordService add()
     */
    @Override
    public boolean add(final String firstName,
                       final String lastName,
                       final String birthDate,
                       final List<String> medications,
                       final List<String> allergies) {
        return this.medicalRecordDAO.add(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordService update()
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final Optional<String> birthDate,
                          final Optional<List<String>> medications,
                          final Optional<List<String>> allergies) {
        return this.medicalRecordDAO.update(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordService delete()
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) {
        return this.medicalRecordDAO.delete(firstName, lastName);
    }
}
