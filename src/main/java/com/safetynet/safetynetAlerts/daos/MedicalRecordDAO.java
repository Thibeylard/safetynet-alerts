package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.MedicalRecord;

import java.util.List;

public interface MedicalRecordDAO {

    /**
     * Add a new MedicalRecord into database.
     *
     * @param firstName   value to set for firstName attribute
     * @param lastName    value to set for lastName attribute
     * @param birthDate   value to set for birthDate attribute
     * @param medications value to set for medications attribute
     * @param allergies   value to set for allergies attribute
     * @return operation success
     */
    public boolean add(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies);

    /**
     * Update specific MedicalRecord from database.
     *
     * @param firstName   value to set for firstName attribute
     * @param lastName    value to set for lastName attribute
     * @param birthDate   value to set for birthDate attribute
     * @param medications value to set for medications attribute
     * @param allergies   value to set for allergies attribute
     * @return operation success
     */
    public boolean update(final String firstName, final String lastName, String birthDate, List<String> medications, List<String> allergies);

    /**
     * Delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    public boolean delete(final String firstName, final String lastName);

    /**
     * Get specific MedicalRecord.dateBirth (converted as age) by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @return age value as String instance
     */
    public String getPersonAge(String firstName, String lastName);

    /**
     * Get specific MedicalRecord by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @return MedicalRecord instance
     */
    public MedicalRecord getPersonMedicalRecord(String firstName, String lastName);

}
