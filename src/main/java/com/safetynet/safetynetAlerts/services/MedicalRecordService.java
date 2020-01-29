package com.safetynet.safetynetAlerts.services;

import org.springframework.stereotype.Service;

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
    public boolean add(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies);

    /**
     * Pass parameters from controllers to DAO to update specific MedicalRecord from database.
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
     * Pass parameters from controllers to DAO to delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    public boolean delete(final String firstName, final String lastName);
}
