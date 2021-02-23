package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import com.safetynet.safetynetAlerts.daos.PersonDAO;
import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public URLFirestationDTO getURLFirestationDTO(final int stationNumber) throws IOException, NoMedicalRecordException {
        try {
            List<String> firestationAddresses = firestationDAO.getFirestations(stationNumber)
                    .stream()
                    .map(Firestation::getAddress)
                    .collect(Collectors.toList());

            List<Person> persons = personDAO.getFromAddress(firestationAddresses, true);

            int adults = 0;

            for (Person person : persons) {
                if (person.isAdult()) {
                    adults += 1;
                }
            }

            int children = persons.size() - adults;

            List<PersonAddressPhoneDTO> personsDTO = new ArrayList<>();
            persons.forEach(person -> personsDTO.add(new PersonAddressPhoneDTO(person)));

            return new URLFirestationDTO(adults, children, personsDTO);
        } catch (NoSuchDataException e) {
            return null;
        }
    }

    /**
     * @see AlertsService getURLChildAlertDTO()
     */
    @Override
    public URLChildAlertDTO getURLChildAlertDTO(final String address) throws IOException, NoMedicalRecordException {
        try {
            List<Person> persons = personDAO.getFromAddress(address, true);

            List<ChildDTO> children = new ArrayList<>();
            List<ChildFamilyMemberDTO> familyMembers = new ArrayList<>();

            for (Person person : persons) {
                if (!person.isAdult()) {
                    children.add(new ChildDTO(person));
                } else {
                    familyMembers.add(new ChildFamilyMemberDTO(person));
                }
            }

            if (children.isEmpty()) {
                familyMembers.clear();
            }

            return new URLChildAlertDTO(children, familyMembers);
        } catch (NoSuchDataException e) {
            return null;
        }
    }

    /**
     * @see AlertsService getPhoneAlertDTO()
     */
    @Override
    public URLPhoneAlertDTO getURLPhoneAlertDTO(int stationNumber) throws IOException {
        try {
            List<String> firestationAddresses = firestationDAO.getFirestations(stationNumber)
                    .stream()
                    .map(Firestation::getAddress)
                    .collect(Collectors.toList());

            List<Person> persons = personDAO.getFromAddress(firestationAddresses, false);
            List<PersonPhoneDTO> personsDTO = new ArrayList<>();

            persons.forEach(person -> personsDTO.add(new PersonPhoneDTO(person)));

            return new URLPhoneAlertDTO(personsDTO);
        } catch (NoSuchDataException | NoMedicalRecordException e) {
            return null;
        }
    }

    /**
     * @see AlertsService getURLFireDTO()
     */
    @Override
    public URLFireDTO getURLFireDTO(String address) throws IOException, NoMedicalRecordException {
        try {
            Firestation firestation = firestationDAO.getFirestation(address);
            List<Person> inhabitants = personDAO.getFromAddress(address, true);

            List<EndangeredPersonDTO> personsDTO = new ArrayList<>();

            for (Person inhabitant : inhabitants) {
                personsDTO.add(new EndangeredPersonDTO(inhabitant));
            }

            return new URLFireDTO(firestation.getStation(), personsDTO);

        } catch (NoSuchDataException e) {
            return null;
        }
    }

    /**
     * @see AlertsService getURLFloodDTO()
     */
    @Override
    public URLFloodDTO getURLFloodDTO(List<Integer> stationNumberList) throws IOException, NoMedicalRecordException {

        try {
            Map<String, List<EndangeredPersonDTO>> addressPersonsMap = new HashMap<>();
            List<Firestation> firestations = new ArrayList<>();
            List<Person> persons = new ArrayList<>();
            List<EndangeredPersonDTO> personDTOS = new ArrayList<>();

            for (Integer station : stationNumberList) {
                firestations.addAll(firestationDAO.getFirestations(station));
                for (Firestation firestation : firestations) {
                    persons.addAll(personDAO.getFromAddress(firestation.getAddress(), true));
                    for (Person person : persons) {
                        personDTOS.add(new EndangeredPersonDTO(person));
                    }
                    addressPersonsMap.put(firestation.getAddress(), new ArrayList<>(personDTOS));
                    personDTOS.clear();
                    persons.clear();
                }
                firestations.clear();
            }

            return new URLFloodDTO(addressPersonsMap);
        } catch (NoSuchDataException e) {
            return null;
        }

    }

    /**
     * @see AlertsService getURLPersonInfoDTO()
     */
    @Override
    public URLPersonInfoDTO getURLPersonInfoDTO(String firstName, String lastName) throws IOException, NoMedicalRecordException {
        try {
            return new URLPersonInfoDTO(personDAO.getFromName(firstName, lastName, true));
        } catch (NoSuchDataException e) {
            return null;
        }
    }

    /**
     * @see AlertsService getURLCommunityEmailDTO()
     */
    @Override
    public URLCommunityEmailDTO getURLCommunityEmailDTO(String city) throws IOException {
        try {
            return new URLCommunityEmailDTO().withPersonsEmails(personDAO.getCommunity(city, false));
        } catch (NoSuchDataException | NoMedicalRecordException e) {
            return null;
        }
    }
}
