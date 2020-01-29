package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return false;
    }

    /**
     * @see FirestationService update()
     */
    @Override
    public boolean update(String address, int number) {
        return false;
    }

    /**
     * @see FirestationService delete(int)
     */
    @Override
    public boolean delete(int number) {
        return false;
    }

    /**
     * @see FirestationService delete(String)
     */
    @Override
    public boolean delete(String address) {
        return false;
    }
}
