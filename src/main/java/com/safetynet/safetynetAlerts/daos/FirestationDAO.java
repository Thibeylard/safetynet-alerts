package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Firestation;

import java.util.List;

public interface FirestationDAO {

    /**
     * Add new Firestation instance to database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    public boolean add(final String address,
                       final int number);

    /**
     * Update specific Firestation number instance from database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    public boolean update(final String address,
                          final int number);

    /**
     * Delete Firestation mapping(s) by number from database.
     *
     * @param number Firestation(s) to delete number attribute value
     * @return operation success
     */
    public boolean delete(final int number);

    /**
     * Delete specified address Firestation mapping from database.
     *
     * @param address Firestation to delete address attribute value
     * @throws Exception for data access failure
     * @return operation success
     */
    public boolean delete(final String address) throws Exception;

    /**
     * Get Firestation address list with specified number as number.
     *
     * @param number number value to search
     * @throws Exception for data access failure
     * @return List of addresses
     */
    public List<String> getFirestationAddresses(final int number) throws Exception;

    /**
     * Get Firestation instance with specified address.
     *
     * @param address address value to search
     * @throws Exception for data access failure
     * @return Firestation instance
     */
    public Firestation getFirestation(final String address) throws Exception;
}
