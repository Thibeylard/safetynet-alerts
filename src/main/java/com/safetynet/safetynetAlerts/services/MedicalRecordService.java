package com.safetynet.safetynetAlerts.services;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

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
                final List<String> allergies);

    /**
     * Pass parameters from controllers to DAO to update specific Person from database.
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
                   final Optional<List<String>> allergies);

    /**
     * Pass parameters from controllers to DAO to delete specific MedicalRecord from database.
     *
     * @param firstName MedicalRecord to delete firstName attribute value
     * @param lastName  MedicalRecord to delete lastName attribute value
     * @return operation success
     */
    boolean delete(final String firstName,
                   final String lastName);
}
