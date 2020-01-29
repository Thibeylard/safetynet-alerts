package com.safetynet.safetynetAlerts.daos;

import com.safetynet.safetynetAlerts.models.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationDAOJsonFile implements FirestationDAO {

    /**
     * JsonFileDatabase singleton instance.
     */
    private final JsonFileDatabase jsonFileDatabase;

    /**
     * Requires JsonFileDatabase to operate on JSON data file.
     *
     * @param jsonFileDatabase instance to initialize this.jsonFileDatabase
     */
    @Autowired
    public FirestationDAOJsonFile(JsonFileDatabase jsonFileDatabase) {
        this.jsonFileDatabase = jsonFileDatabase;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean add(String address, int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean update(String address, int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     * @param number
     */
    @Override
    public boolean delete(int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     * @param address
     */
    @Override
    public boolean delete(String address) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public List<String> getFirestationAddresses(int pNumber) {
        return null;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public Firestation getFirestation(String address) {
        return null;
    }
}
