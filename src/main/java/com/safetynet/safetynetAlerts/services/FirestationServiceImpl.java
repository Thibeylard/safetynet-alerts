package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

@Service
public class FirestationServiceImpl implements FirestationService {

    private FirestationDAO firestationDAO;

    @Autowired
    public FirestationServiceImpl(FirestationDAO firestationDAO) {
        this.firestationDAO = firestationDAO;
    }

    /**
     * @see FirestationService add()
     */
    @Override
    public boolean add(String address, int number) {
        Logger.debug("Firestation Service pass add request to DAO");
        return this.firestationDAO.add(address,number);
    }

    /**
     * @see FirestationService update()
     */
    @Override
    public boolean update(String address, int number) {
        Logger.debug("Firestation Service pass update request to DAO");
        return this.firestationDAO.update(address,number);
    }

    /**
     * @see FirestationService delete(int)
     */
    @Override
    public boolean delete(int number) {
        Logger.debug("Firestation Service pass delete request to DAO");
        return this.firestationDAO.delete(number);
    }

    /**
     * @see FirestationService delete(String)
     */
    @Override
    public boolean delete(String address) {
        Logger.debug("Firestation Service pass delete request to DAO");
        return this.firestationDAO.delete(address);
    }
}
