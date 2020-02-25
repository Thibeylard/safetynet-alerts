package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface FirestationService {

    /**
     * Pass parameters from controllers to DAO to add new Firestation instance into database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    boolean add(final String address, final int number) throws IOException, IllegalDataOverrideException;

    /**
     * Pass parameters from controllers to DAO to update specific Firestation number instance from database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    boolean update(final String address, final int number) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to delete Firestation mapping(s) by number from database.
     *
     * @param number Firestation(s) to delete number attribute value
     * @return operation success
     */
    boolean delete(final int number) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to delete specified address Firestation mapping from database.
     *
     * @param address Firestation to delete address attribute value
     * @return operation success
     */
    boolean delete(final String address) throws IOException, NoSuchDataException;

    /**
     * Pass parameters from controllers to DAO to get specified address Firestation mapping from database.
     *
     * @param address Firestation to get address attribute value
     * @return operation success
     */
    Firestation get(String address) throws IOException, NoSuchDataException;
}
