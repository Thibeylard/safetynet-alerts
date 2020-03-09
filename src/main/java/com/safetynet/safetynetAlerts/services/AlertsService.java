package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AlertsService {

    /**
     * Create a new URLFirestationDTO instance based on received parameters and database.
     *
     * @param stationNumber Firestation number from which requested inhabitants depend.
     * @return URLFirestationDTO instance
     * @see URLFirestationDTO data format
     */
    URLFirestationDTO getURLFirestationDTO(final int stationNumber) throws IOException, NoMedicalRecordException;

    /**
     * Create a new URLChildAlertDTO instance based on received parameters and database.
     *
     * @param address Address where children infos are requested.
     * @return URLChildAlertDTO instance
     * @see URLChildAlertDTO data format
     */
    URLChildAlertDTO getURLChildAlertDTO(final String address) throws IOException, NoMedicalRecordException;

    /**
     * Create a new URLPhoneAlertDTO instance based on received parameters and database.
     *
     * @param stationNumber Firestation number of inhabitants from whom phone numbers are requested.
     * @return URLPhoneAlertDTO instance
     * @see URLPhoneAlertDTO data format
     */
    URLPhoneAlertDTO getURLPhoneAlertDTO(final int stationNumber) throws IOException;

    /**
     * Create a new URLFireDTO instance based on received parameters and database.
     *
     * @param address Address where endangered inhabitants medical infos are requested in fire accident.
     * @return URLFireDTO instance
     * @see URLFireDTO data format
     */
    URLFireDTO getURLFireDTO(final String address) throws IOException, NoMedicalRecordException;

    /**
     * Create a new URLFloodDTO instance based on received parameters and database.
     *
     * @param stationNumberList All stations number responsible of endangered inhabitants in a flood accident.
     * @return URLFireDTO instance
     * @see URLFloodDTO data format
     */
    URLFloodDTO getURLFloodDTO(List<Integer> stationNumberList) throws IOException, NoMedicalRecordException;

    /**
     * Create a new URLPersonInfoDTO instance based on received parameters and database.
     *
     * @param firstName firstName of the person about which to get infos
     * @param lastName  lastName of the person about which to get infos
     * @return URLPersonInfoDTO instance
     * @see URLPersonInfoDTO data format
     */
    URLPersonInfoDTO getURLPersonInfoDTO(final String firstName, final String lastName) throws IOException, NoMedicalRecordException;

    /**
     * Create a new URLCommunityEmailDTO instance based on received parameters and database.
     *
     * @param city Name of the city from which to look for inhabitants emails
     * @return URLCommunityEmailDTO instance
     * @see URLCommunityEmailDTO data format
     */
    URLCommunityEmailDTO getURLCommunityEmailDTO(final String city) throws IOException;
}
