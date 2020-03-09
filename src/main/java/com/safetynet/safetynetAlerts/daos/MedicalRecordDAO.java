package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
    boolean add(final String firstName,
                final String lastName,
                final String birthDate,
                final List<String> medications,
                final List<String> allergies) throws IOException, IllegalDataOverrideException;

    /**
     * Update specific MedicalRecord from database.
     *
     * @param firstName   value to set for firstName attribute
     * @param lastName    value to set for lastName attribute
     * @param birthDate   optional value to set for birthDate attribute
     * @param medications optional value to set for birthDate attribute
     * @param allergies   optional value to set for birthDate attribute
     * @return operation success
     */
    boolean update(final String firstName,
                   final String lastName,
                   final Optional<String> birthDate,
                   final Optional<List<String>> medications,
                   final Optional<List<String>> allergies) throws IOException, NoSuchDataException;

    /**
     * Delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName,
                   final String lastName) throws IOException, NoSuchDataException;

    /**
     * Get Firestation instance with specified address.
     *
     * @param firstName MedicalRecord to access firstName attribute value
     * @param lastName  MedicalRecord to access lastName attribute value
     * @throws IOException, NoSuchDataException for data access failure
     */
    MedicalRecord get(final String firstName,
                      final String lastName) throws IOException, NoSuchDataException;
}
