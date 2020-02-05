package com.safetynet.safetynetAlerts.services;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

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
    boolean add(String firstName, String lastName, String birthDate, List<String> medications, List<String> allergies);

    /**
     * Pass parameters from controllers to DAO to update specific Person from database.
     *
     * @param firstName value to set for firstName attribute
     * @param lastName  value to set for lastName attribute
     * @param optionalParams values to set for Person (must contains at least one key)
     * @return operation success
     */
    boolean update(final String firstName, final String lastName, final MultiValueMap<String,String> optionalParams);

    /**
     * Pass parameters from controllers to DAO to delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName, final String lastName);
}
