package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import java.io.IOException;

@Service
public class FirestationServiceImpl implements FirestationService {

    private FirestationDAO firestationDAO;

    @Autowired
    public FirestationServiceImpl(FirestationDAO firestationDAO) {
        this.firestationDAO = firestationDAO;
    }

    /**
     * @see FirestationService
     */
    @Override
    public boolean add(String address, int number) throws IOException, IllegalDataOverrideException {
        Logger.debug("Firestation Service pass add request to DAO");
        return this.firestationDAO.add(address, number);
    }

    /**
     * @see FirestationService
     */
    @Override
    public boolean update(String address, int number) throws IOException, NoSuchDataException {
        Logger.debug("Firestation Service pass update request to DAO");
        return this.firestationDAO.update(address, number);
    }

    /**
     * @see FirestationService
     */
    @Override
    public boolean delete(int number) throws IOException, NoSuchDataException {
        Logger.debug("Firestation Service pass delete request to DAO");
        return this.firestationDAO.delete(number);
    }

    /**
     * @see FirestationService
     */
    @Override
    public boolean delete(String address) throws IOException, NoSuchDataException {
        Logger.debug("Firestation Service pass delete request to DAO");
        return this.firestationDAO.delete(address);
    }

    /**
     * @see FirestationService
     */
    @Override
    public Firestation get(String address) throws IOException, NoSuchDataException {
        return firestationDAO.getFirestation(address);
    }
}
