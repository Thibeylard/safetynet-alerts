package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import org.tinylog.Logger;

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
                       final List<String> allergies) {
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
                          final Optional<List<String>> allergies) {
        Logger.debug("MedicalRecord DAO pass update request to JsonFileDatabase");
        return jsonFileDatabase.updateMedicalRecord(firstName, lastName, birthDate, medications, allergies);
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) {
        Logger.debug("MedicalRecord DAO pass delete request to JsonFileDatabase");
        return jsonFileDatabase.deleteMedicalRecord(firstName, lastName);
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public MedicalRecord getPersonMedicalRecord(final String firstName,
                                                final String lastName) throws Exception {
        return null;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public List<MedicalRecord> getPersonsMedicalRecord(List<Person> persons) throws Exception {
        return null;
    }

    /**
     * Get adults MedicalRecords from database.
     *
     * @return MedicalRecord instance
     */
    @Override
    public List<MedicalRecord> getAdultMedicalRecords() throws Exception {
        return null;
    }

    /**
     * Get children MedicalRecords from database.
     *
     * @return MedicalRecord instance
     */
    @Override
    public List<MedicalRecord> getChildrenMedicalRecords() throws Exception {
        return null;
    }

}
