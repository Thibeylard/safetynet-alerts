package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import com.safetynet.safetynetAlerts.daos.PersonDAO;
import com.safetynet.safetynetAlerts.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertsServiceImpl implements AlertsService {

    private FirestationDAO firestationDAO;
    private MedicalRecordDAO medicalRecordDAO;
    private PersonDAO personDAO;

    @Autowired
    public AlertsServiceImpl(FirestationDAO firestationDAO, MedicalRecordDAO medicalRecordDAO, PersonDAO personDAO) {
        this.firestationDAO = firestationDAO;
        this.medicalRecordDAO = medicalRecordDAO;
        this.personDAO = personDAO;
    }

    /**
     * @see AlertsService getURLFirestationDTO()
     */
    @Override
    public URLFirestationDTO getURLFirestationDTO(int stationNumber) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getURLChildAlertDTO()
     */
    @Override
    public URLChildAlertDTO getURLChildAlertDTO(String address) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getPhoneAlertDTO()
     */
    @Override
    public URLPhoneAlertDTO getURLPhoneAlertDTO(int stationNumber) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getURLFireDTO()
     */
    @Override
    public URLFireDTO getURLFireDTO(String address) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getURLFloodDTO()
     */
    @Override
    public URLFloodDTO getURLFloodDTO(List<Integer> stationNumberList) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getURLPersonInfoDTO()
     */
    @Override
    public URLPersonInfoDTO getURLPersonInfoDTO(String firstName, String lastName) throws Exception {
        return null;
    }

    /**
     * @see AlertsService getURLCommunityEmailDTO()
     */
    @Override
    public URLCommunityEmailDTO getURLCommunityEmailDTO(String city) throws Exception {
        return null;
    }
}
