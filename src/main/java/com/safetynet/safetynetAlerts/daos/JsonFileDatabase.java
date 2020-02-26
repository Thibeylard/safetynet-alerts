package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Repository
public class JsonFileDatabase {

    private final JsonFactory factory;
    private File data;

    @Value("${json-database.src}")
    private String src;

    private JsonFileDatabaseDTO jsonFileDTO;

    /**
     * Constructor.
     *
     * @param jsonFactory Autowired JsonFactory Singleton
     * @throws IOException Handles dataSrc file related errors
     */

    @Autowired
    public JsonFileDatabase(final JsonFactory jsonFactory, ObjectMapper mapper) throws IOException {
        this.data = new File(this.src);
        this.factory = jsonFactory.setCodec(mapper);
        JsonParser parser = factory.createParser(this.data);
        this.jsonFileDTO = parser.readValueAs(JsonFileDatabaseDTO.class);
    }

    private boolean writeDataToJsonFile() throws IOException {
        try {
            JsonGenerator generator = factory.createGenerator(this.data, JsonEncoding.UTF8);
            generator.writeStartObject();
            generator.writeObjectField("persons", this.jsonFileDTO.getPersons());
            generator.writeObjectField("firestations", this.jsonFileDTO.getFirestations());
            generator.writeObjectField("medicalrecords", this.jsonFileDTO.getMedicalRecords());
            generator.writeEndObject();
            generator.close();
            Logger.debug("data.json successfully saved.");
        } catch (IOException e) {
            Logger.error("An IOException occurred : data.json save failed.");
            throw new IOException();
        }

        return true;
    }

//    ---------------------------------------------------------------------------------------- FIRESTATION

    public Firestation getFirestation(final String address) throws IOException, NoSuchDataException {
        return null;
    }

    public List<Firestation> getFirestations(final int number) throws IOException, NoSuchDataException {
        return null;
    }

    public boolean addFirestation(final String address, final int number) throws IOException, IllegalDataOverrideException {
        Optional<Firestation> existantFirestation = this.jsonFileDTO.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst();

        if (existantFirestation.isPresent()) {
            throw new IllegalDataOverrideException();
        } else {
            this.jsonFileDTO.getFirestations().add(new Firestation(address, number));
        }

        return writeDataToJsonFile();
    }

    public boolean updateFirestation(final String address, final int number) throws IOException, NoSuchDataException {

        this.jsonFileDTO.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst()
                .orElseThrow(NoSuchDataException::new)
                .setStation(number);

        return writeDataToJsonFile();
    }

    public boolean deleteFirestation(final int number) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getStation() == number)) {
            throw new NoSuchDataException();
        }
        return writeDataToJsonFile();
    }

    public boolean deleteFirestation(final String address) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getAddress().equals(address))) {
            throw new NoSuchDataException();
        }
        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- MEDICALRECORD

    public MedicalRecord getMedicalRecord(final String firstName, final String lastName) throws IOException, NoSuchDataException {
        return null;
    }

    public List<MedicalRecord> getMedicalRecords(final List<Person> person) throws IOException, NoSuchDataException {
        return null;
    }

    public boolean addMedicalRecord(final String firstName,
                                    final String lastName,
                                    final String birthDate,
                                    final List<String> medications,
                                    final List<String> allergies) throws IOException, IllegalDataOverrideException {

        Optional<MedicalRecord> existantMedicalRecord = this.jsonFileDTO.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName))
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName))
                .findFirst();

        if (existantMedicalRecord.isPresent()) {
            throw new IllegalDataOverrideException();
        } else {
            this.jsonFileDTO.getMedicalRecords().add(new MedicalRecord(
                    firstName,
                    lastName,
                    birthDate,
                    medications,
                    allergies));
        }

        return writeDataToJsonFile();
    }

    public boolean updateMedicalRecord(final String firstName,
                                       final String lastName,
                                       final Optional<String> birthDate,
                                       final Optional<List<String>> medications,
                                       final Optional<List<String>> allergies) throws IOException, NoSuchDataException {

        MedicalRecord existantMedicalRecord = this.jsonFileDTO.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName))
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(NoSuchDataException::new);

        birthDate.ifPresent(existantMedicalRecord::setBirthDate);
        medications.ifPresent(existantMedicalRecord::setMedications);
        allergies.ifPresent(existantMedicalRecord::setAllergies);

        return writeDataToJsonFile();
    }

    public boolean deleteMedicalRecord(final String firstName,
                                       final String lastName) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getMedicalRecords().removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))) {
            throw new NoSuchDataException();
        }

        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- PERSON

    public Person getPerson(final String firstName,
                            final String lastName) throws NoSuchDataException {
        return this.jsonFileDTO.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .filter(person -> person.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(NoSuchDataException::new);
    }

    public List<Person> getPersonFromLastName(final String firstName,
                                              final String lastName) throws IOException, NoSuchDataException {
        return null;
    }

    public List<Person> getPersonFromAddress(final String address) throws IOException, NoSuchDataException {
        return null;
    }

    public List<Person> getPersonFromAddress(final List<String> address) throws IOException, NoSuchDataException {
        return null;
    }

    public List<Person> getPersonFromCity(final String city) throws IOException, NoSuchDataException {
        return null;
    }

    public boolean addPerson(final String firstName,
                             final String lastName,
                             final String address,
                             final String city,
                             final String zip,
                             final String phone,
                             final String email) throws IOException, IllegalDataOverrideException {

        Optional<Person> existantPerson = this.jsonFileDTO.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .filter(person -> person.getFirstName().equals(firstName))
                .findFirst();

        if (existantPerson.isPresent()) {
            throw new IllegalDataOverrideException();
        } else {
            this.jsonFileDTO.getPersons().add(new Person(
                    firstName,
                    lastName,
                    address,
                    city,
                    zip,
                    phone,
                    email));

            return writeDataToJsonFile();
        }

    }

    public boolean updatePerson(final String firstName,
                                final String lastName,
                                final Optional<String> address,
                                final Optional<String> city,
                                final Optional<String> zip,
                                final Optional<String> phone,
                                final Optional<String> email) throws IOException, NoSuchDataException {

        Person existantPerson = this.jsonFileDTO.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .filter(person -> person.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(NoSuchDataException::new);


        address.ifPresent(existantPerson::setAddress);
        city.ifPresent(existantPerson::setCity);
        zip.ifPresent(existantPerson::setZip);
        phone.ifPresent(existantPerson::setPhone);
        email.ifPresent(existantPerson::setEmail);

        return writeDataToJsonFile();
    }

    public boolean deletePerson(final String firstName,
                                final String lastName) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getPersons().removeIf(person ->
                person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))) {
            throw new NoSuchDataException();
        }

        return writeDataToJsonFile();
    }

}
