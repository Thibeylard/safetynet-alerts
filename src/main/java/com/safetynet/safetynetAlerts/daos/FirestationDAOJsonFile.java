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
    public FirestationDAOJsonFile(JsonFileDatabase jsonFileDatabase) throws Exception {
        this.jsonFileDatabase = jsonFileDatabase;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean add(final String address,
                       final int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean update(final String address,
                          final int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean delete(final int number) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public boolean delete(final String address) {
        return false;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public List<String> getFirestationAddresses(final int number) throws Exception {
        return null;
    }

    /**
     * @see FirestationDAO
     */
    @Override
    public Firestation getFirestation(final String address) throws Exception {
        return null;
    }
}
