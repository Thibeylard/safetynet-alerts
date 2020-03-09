package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;

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
     * Get specific MedicalRecord by name from database.
     *
     * @param firstName firstName value to search
     * @param lastName  lastName value to search
     * @return MedicalRecord instance
     * @throws IOException for data access failure
     */
    MedicalRecord getMedicalRecord(final String firstName,
                                   final String lastName) throws IOException, NoSuchDataException;

    /**
     * Get MedicalRecords of all Persons in the list from database.
     *
     * @param person Person of which to get MedicalRecord
     * @return List of MedicalRecord instance
     * @throws IOException for data access failure
     */
    MedicalRecord getMedicalRecord(Person person) throws IOException, NoSuchDataException;

    /**
     * Get MedicalRecords of Persons List from database.
     *
     * @param persons List of Persons of which to get MedicalRecords
     * @return MedicalRecord instance
     * @throws IOException         for data access failure
     * @throws NoSuchDataException for data access failure
     */
    List<MedicalRecord> getMedicalRecords(final List<Person> persons) throws IOException, NoSuchDataException;


}
