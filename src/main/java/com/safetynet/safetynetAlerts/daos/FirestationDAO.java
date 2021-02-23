package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;

import java.io.IOException;
import java.util.List;

public interface FirestationDAO {

    /**
     * Add new Firestation instance to database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    boolean add(final String address,
                final int number) throws IOException, IllegalDataOverrideException;

    /**
     * Update specific Firestation number instance from database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    boolean update(final String address,
                   final int number) throws IOException, NoSuchDataException;

    /**
     * Delete Firestation mapping(s) by number from database.
     *
     * @param number Firestation(s) to delete number attribute value
     * @return operation success
     */
    boolean delete(final int number) throws IOException, NoSuchDataException;

    /**
     * Delete specified address Firestation mapping from database.
     *
     * @param address Firestation to delete address attribute value
     * @return operation success
     */
    boolean delete(final String address) throws IOException, NoSuchDataException;

    /**
     * Get Firestation instance with specified address.
     *
     * @param address address value to search
     * @return Firestation instance
     * @throws IOException, IllegalDataOverrideException, NoSuchDataException for data access failure
     */
    Firestation getFirestation(final String address) throws IOException, NoSuchDataException;

    /**
     * Get Firestation address list with specified number as number.
     *
     * @param number number value to search
     * @return List of addresses
     * @throws IOException, IllegalDataOverrideException, NoSuchDataException for data access failure
     */
    List<Firestation> getFirestations(final int number) throws IOException, NoSuchDataException;
}
