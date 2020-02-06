package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

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
    public boolean add(final String firstName,
                       final String lastName,
                       final String birthDate,
                       final List<String> medications,
                       final List<String> allergies) {
        return false;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean update(final String firstName,
                          final String lastName,
                          final MultiValueMap<String, String> optionalParams) {
        return false;
    }

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public boolean delete(final String firstName,
                          final String lastName) {
        return false;
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

    /**
     * @see MedicalRecordDAO
     */
    @Override
    public String getPersonAge(final String firstName,
                               final String lastName) throws Exception {
        return null;
    }
}
