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
    public boolean add(final String firstName,
                       final String lastName,
                       final String birthDate,
                       final List<String> medications,
                       final List<String> allergies)
            throws Exception;

    /**
     * Update specific MedicalRecord from database.
     *
     * @param firstName   value to set for firstName attribute
     * @param lastName    value to set for lastName attribute
     * @param birthDate   value to set for birthDate attribute
     * @param medications value to set for medications attribute
     * @param allergies   value to set for allergies attribute
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean update(final String firstName,
                          final String lastName,
                          final String birthDate,
                          final List<String> medications,
                          final List<String> allergies)
            throws Exception;

    /**
     * Delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean delete(final String firstName,
                          final String lastName)
            throws Exception;

    /**
     * Get specific MedicalRecord by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @throws Exception for data access failure
     * @return MedicalRecord instance
     */
    public MedicalRecord getPersonMedicalRecord(final String firstName,
                                                final String lastName)
            throws Exception;

    /**
     * Get adults MedicalRecords from database.
     *
     * @throws Exception for data access failure
     * @return MedicalRecord instance
     */
    public List<MedicalRecord> getAdultMedicalRecords()
            throws Exception;

    /**
     * Get children MedicalRecords from database.
     *
     * @throws Exception for data access failure
     * @return MedicalRecord instance
     */
    public List<MedicalRecord> getChildrenMedicalRecords()
            throws Exception;

    /**
     * Get specific MedicalRecord.dateBirth (converted as age) by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @throws Exception for data access failure
     * @return age value as String instance
     */
    public String getPersonAge(final String firstName,
                               final String lastName)
            throws Exception;

}
