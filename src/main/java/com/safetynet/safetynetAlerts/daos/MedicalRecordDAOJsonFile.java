package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicalRecordDAOJsonFile implements MedicalRecordDAO {

    /**
     * JsonFileDatabase singleton instance.
     */
    private final JsonFileDatabase jsonFileDatabase;

    /**
     * Requires JsonFileDatabase to operate on JSON data file.
     *
     * @param jsonFileDatabase instance to initialize this.jsonFileDatabase
     */
    @Autowired
    public MedicalRecordDAOJsonFile(final JsonFileDatabase jsonFileDatabase) {
        this.jsonFileDatabase = jsonFileDatabase;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean add(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies) {
        return false;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean update(final String firstName, final String lastName, String birthDate, List<String> medications, List<String> allergies) {
        return false;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean delete(final String firstName, final String lastName) {
        return false;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public String getPersonAge(String firstName, String lastName) {
        return null;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public MedicalRecord getPersonMedicalRecord(String firstName, String lastName) {
        return null;
    }
}
