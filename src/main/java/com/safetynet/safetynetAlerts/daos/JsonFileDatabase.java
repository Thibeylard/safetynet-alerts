package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonFileDatabase {

    private final ObjectMapper MAPPER;
    private final File DATA;

    private JsonFileDatabaseDTO jsonFileDTO;


    /**
     * Constructor.
     *
     * @param pMapper Autoriwed ObjectMapper Singleton
     * @throws IOException Handles dataSrc file related errors
     */
    @Autowired
    public JsonFileDatabase(final ObjectMapper pMapper) throws IOException {
        this.DATA = new File("src/main/resources/static/data.json");
        this.MAPPER = pMapper;
        this.jsonFileDTO = MAPPER.convertValue(MAPPER.readTree(DATA), new TypeReference<JsonFileDatabaseDTO>() {
        });
    }


    private boolean writeDataToJsonFile() {
        try {
            MAPPER.writeValue(DATA, this.jsonFileDTO);
            Logger.debug("data.json successfully saved.");
            return true;
        } catch (IOException e) {
            Logger.error("An IOException occurred : data.json save failed.");
            return false;
        }
    }

//    ---------------------------------------------------------------------------------------- FIRESTATION

    public boolean addFirestation(final String address, final int number) {
        this.jsonFileDTO.getFirestations().add(new Firestation(address, number));

        return writeDataToJsonFile();
    }

    public boolean updateFirestation(final String address, final int number) {
        this.jsonFileDTO.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .forEach(firestation -> firestation.setStation(number));

        return writeDataToJsonFile();
    }

    public boolean deleteFirestation(final int number) {
        jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getStation() == number);

        return writeDataToJsonFile();
    }

    public boolean deleteFirestation(final String address) {
        jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getAddress().equals(address));

        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- MEDICALRECORD

    public boolean addMedicalRecord(final String firstName,
                                    final String lastName,
                                    final String birthDate,
                                    final List<String> medications,
                                    final List<String> allergies) {
        this.jsonFileDTO.getMedicalRecords().add(new MedicalRecord(
                firstName,
                lastName,
                birthDate,
                medications,
                allergies
        ));

        return writeDataToJsonFile();
    }

    public boolean updateMedicalRecord(final String firstName,
                                       final String lastName,
                                       final Optional<String> birthDate,
                                       final Optional<List<String>> medications,
                                       final Optional<List<String>> allergies) {
        this.jsonFileDTO.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName))
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName))
                .forEach(medicalRecord -> {
                    birthDate.ifPresent(medicalRecord::setBirthDate);
                    medications.ifPresent(medicalRecord::setMedications);
                    allergies.ifPresent(medicalRecord::setAllergies);
                });

        return writeDataToJsonFile();
    }

    public boolean deleteMedicalRecord(final String firstName,
                                       final String lastName) {
        jsonFileDTO.getMedicalRecords().removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName));
        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- PERSON

    public boolean addPerson(final String firstName,
                             final String lastName,
                             final String address,
                             final String city,
                             final String zip,
                             final String phone,
                             final String email) {
        this.jsonFileDTO.getPersons().add(new Person(
                firstName,
                lastName,
                address,
                city,
                zip,
                phone,
                email,
                Optional.empty()
        ));

        return writeDataToJsonFile();
    }

    public boolean updatePerson(final String firstName,
                                final String lastName,
                                final Optional<String> address,
                                final Optional<String> city,
                                final Optional<String> zip,
                                final Optional<String> phone,
                                final Optional<String> email) {
        this.jsonFileDTO.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .filter(person -> person.getFirstName().equals(firstName))
                .forEach(person -> {
                    address.ifPresent(person::setAddress);
                    city.ifPresent(person::setCity);
                    zip.ifPresent(person::setZip);
                    phone.ifPresent(person::setPhone);
                    email.ifPresent(person::setEmail);
                });

        return writeDataToJsonFile();
    }

    public boolean deletePerson(final String firstName,
                                final String lastName) {
        jsonFileDTO.getPersons().removeIf(person ->
                person.getFirstName().equals(firstName) && person.getLastName().equals(lastName));
        return writeDataToJsonFile();
    }

}
