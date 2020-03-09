package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    public boolean add(final String firstName,
                       final String lastName,
                       final String birthDate,
                       final List<String> medications,
                       final List<String> allergies) throws IOException, IllegalDataOverrideException {
        Logger.debug("MedicalRecord DAO pass add request to JsonFileDatabase");
        return jsonFileDatabase.addMedicalRecord(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final Optional<String> birthDate,
                          final Optional<List<String>> medications,
                          final Optional<List<String>> allergies) throws IOException, NoSuchDataException {
        Logger.debug("MedicalRecord DAO pass update request to JsonFileDatabase");
        return jsonFileDatabase.updateMedicalRecord(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) throws IOException, NoSuchDataException {
        Logger.debug("MedicalRecord DAO pass delete request to JsonFileDatabase");
        return jsonFileDatabase.deleteMedicalRecord(firstName, lastName);
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public MedicalRecord get(String firstName, String lastName) throws IOException, NoSuchDataException {
        return jsonFileDatabase.getMedicalRecord(firstName, lastName);
    }
}
