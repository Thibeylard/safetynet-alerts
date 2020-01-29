package com.safetynet.safetynetAlerts.services;

import org.springframework.stereotype.Service;

@Service
public interface FirestationService {

    /**
     * Pass parameters from controllers to DAO to add new Firestation instance into database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    public boolean add(final String address, final int number);

    /**
     * Pass parameters from controllers to DAO to update specific Firestation number instance from database.
     *
     * @param address value to set for address attribute
     * @param number  value to set for number attribute
     * @return operation success
     */
    public boolean update(final String address, final int number);

    /**
     * Pass parameters from controllers to DAO to delete Firestation mapping(s) by number from database.
     *
     * @param number Firestation(s) to delete number attribute value
     * @return operation success
     */
    public boolean delete(final int number);

    /**
     * Pass parameters from controllers to DAO to delete specified address Firestation mapping from database.
     *
     * @param address Firestation to delete address attribute value
     * @return operation success
     */
    public boolean delete(final String address);
}
