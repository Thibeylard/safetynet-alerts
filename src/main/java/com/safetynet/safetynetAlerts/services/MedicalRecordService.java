package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface MedicalRecordService {

    /**
     * Pass parameters from controllers to DAO to add a new MedicalRecord into database.
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
     * Pass parameters from controllers to DAO to update specific Person in database.
     *
     * @param firstName   value to set for firstName attribute
     * @param lastName    value to set for lastName attribute
     * @param birthDate   value to set for birthDate attribute (Nullable)
     * @param medications value to set for medications attribute (Nullable)
     * @param allergies   value to set for allergies attribute (Nullable)
     * @return operation success
     */
    boolean update(final String firstName,
                   final String lastName,
                   final String birthDate,
                   final List<String> medications,
                   final List<String> allergies) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to delete specific MedicalRecord in database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName,
                   final String lastName) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to get specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to get firstName attribute value
     * @param lastName  MedicalRecord to get lastName attribute value
     * @return operation success
     */
    MedicalRecord get(String firstName, String lastName) throws IOException, NoSuchDataException;
}
