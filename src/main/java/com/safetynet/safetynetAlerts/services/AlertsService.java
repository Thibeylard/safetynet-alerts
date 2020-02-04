package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.dtos.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlertsService {

    /**
     * Create a new URLFirestationDTO instance based on received parameters and database.
     * @see URLFirestationDTO data format
     * @param stationNumber Firestation number from which requested inhabitants depend.
     * @return URLFirestationDTO instance
     */
    public URLFirestationDTO getURLFirestationDTO(final int stationNumber) throws Exception;

    /**
     * Create a new URLChildAlertDTO instance based on received parameters and database.
     * @see URLChildAlertDTO data format
     * @param address Address where children infos are requested.
     * @return URLChildAlertDTO instance
     */
    public URLChildAlertDTO getURLChildAlertDTO(final String address) throws Exception;

    /**
     * Create a new URLPhoneAlertDTO instance based on received parameters and database.
     * @see URLPhoneAlertDTO data format
     * @param stationNumber Firestation number of inhabitants from whom phone numbers are requested.
     * @return URLPhoneAlertDTO instance
     */
    public URLPhoneAlertDTO getPhoneAlertDTO(final int stationNumber) throws Exception;

    /**
     * Create a new URLFireDTO instance based on received parameters and database.
     * @see URLFireDTO data format
     * @param address Address where endangered inhabitants medical infos are requested in fire accident.
     * @return URLFireDTO instance
     */
    public URLFireDTO getURLFireDTO(final String address) throws Exception;

    /**
     * Create a new URLFloodDTO instance based on received parameters and database.
     * @see URLFloodDTO data format
     * @param stationNumberList All stations number responsible of endangered inhabitants in a flood accident.
     * @return URLFireDTO instance
     */
    public URLFloodDTO getURLFloodDTO(List<Integer> stationNumberList) throws Exception;

    /**
     * Create a new URLPersonInfoDTO instance based on received parameters and database.
     * @see URLPersonInfoDTO data format
     * @param firstName firstName of the person about which to get infos
     * @param lastName lastName of the person about which to get infos
     * @return URLPersonInfoDTO instance
     */
    public URLPersonInfoDTO getURLPersonInfoDTO(final String firstName, final String lastName) throws Exception;

    /**
     * Create a new URLCommunityEmailDTO instance based on received parameters and database.
     * @see URLCommunityEmailDTO data format
     * @param city Name of the city from which to look for inhabitants emails
     * @return URLCommunityEmailDTO instance
     */
    public URLCommunityEmailDTO getURLCommunityEmailDTO(final String city) throws Exception;
}
