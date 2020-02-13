package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.models.Firestation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JsonFileDatabase {

    private final ObjectMapper mapper;
    private JsonFileDatabaseDTO jsonFileDTO;
    private File dataFile;


    /**
     * Constructor.
     *
     * @param pMapper Autoriwed ObjectMapper Singleton
     * @throws IOException Handles dataSrc file related errors
     */
    @Autowired
    public JsonFileDatabase(final ObjectMapper pMapper) throws IOException {
        this.mapper = pMapper;
        this.dataFile = new File("src/main/resources/static/data.json");
        this.jsonFileDTO = mapper.convertValue(mapper.readTree(dataFile), new TypeReference<JsonFileDatabaseDTO>() {
        });
    }

    /**
     * Setter for dataFile attribute
     *
     * @param dataFile new File to set.
     */
    public void setDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    private boolean writeDataToJsonFile() {
        try {
            mapper.writeValue(dataFile, this.jsonFileDTO);
            return true;
        } catch (IOException e) {
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
        return false;
    }

    public boolean updateMedicalRecord(final String firstName,
                                       final String lastName,
                                       final Optional<String> birthDate,
                                       final Optional<List<String>> medications,
                                       final Optional<List<String>> allergies) {
        return false;
    }

    public boolean deleteMedicalRecord(final String firstName,
                                       final String lastName) {
        return false;
    }

    //    ---------------------------------------------------------------------------------------- PERSON

    public boolean addPerson(final String firstName,
                             final String lastName,
                             final String address,
                             final String city,
                             final String zip,
                             final String phone,
                             final String email) {
        return false;
    }

    public boolean updatePerson(final String firstName,
                                final String lastName,
                                final Optional<String> address,
                                final Optional<String> city,
                                final Optional<String> zip,
                                final Optional<String> phone,
                                final Optional<String> email) {
        return false;
    }

    public boolean deletePerson(final String firstName,
                                final String lastName) {
        return false;
    }

}
