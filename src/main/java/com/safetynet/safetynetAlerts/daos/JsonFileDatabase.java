package com.safetynet.safetynetAlerts.daos;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.JsonFileDatabaseDTO;
import com.safetynet.safetynetAlerts.exceptions.IllegalDataOverrideException;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.MedicalRecord;
import com.safetynet.safetynetAlerts.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JsonFileDatabase {

    private final JsonFactory factory;
    private File data;

    private JsonFileDatabaseDTO jsonFileDTO;

    /**
     * Constructor.
     *
     * @param jsonFactory Autowired JsonFactory Singleton
     * @throws IOException Handles dataSrc file related errors
     */
    @Autowired
    public JsonFileDatabase(final JsonFactory jsonFactory,
                            final ObjectMapper mapper,
                            @Value("${jsondatabase.src}") String src) throws IOException {
        this.data = new File(src);
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

    public Firestation getFirestation(final String address) throws NoSuchDataException {
        Logger.debug("Search JsonDatabase for Firestation with address : {}.", address);
        Firestation firestation;
        try {
            firestation = this.jsonFileDTO.getFirestations().stream()
                    .filter(f -> f.getAddress().equals(address))
                    .findFirst()
                    .orElseThrow(NoSuchDataException::new);
        } catch (NoSuchDataException e) {
            Logger.debug("No matched result.");
            throw e;
        }

        Logger.debug("Matched Firestation");
        return firestation;
    }

    public List<Firestation> getFirestations(final int number) throws NoSuchDataException {
        Logger.debug("Search JsonDatabase for Firestations with station number : {}.", number);
        List<Firestation> firestations = this.jsonFileDTO.getFirestations().stream()
                .filter(firestation -> firestation.getStation() == number)
                .collect(Collectors.toList());

        if (firestations.isEmpty()) {
            Logger.debug("No matched result.");
            throw new NoSuchDataException();
        }

        Logger.debug("Matched {} Firestation(s)", firestations.size());

        return firestations;
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

    public MedicalRecord getMedicalRecord(final String firstName, final String lastName) throws NoSuchDataException {
        Logger.debug("Search JsonDatabase for MedicalRecord with name : {} {}.", firstName, lastName);
        MedicalRecord medicalRecord;

        try {
            medicalRecord = this.jsonFileDTO.getMedicalRecords().stream()
                    .filter(mr -> mr.getLastName().equals(lastName))
                    .filter(mr -> mr.getFirstName().equals(firstName))
                    .findFirst()
                    .orElseThrow(NoSuchDataException::new);
        } catch (NoSuchDataException e) {
            Logger.debug("No matched result.");
            throw e;
        }

        Logger.debug("Matched MedicalRecord");
        return medicalRecord;
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
                                       final String birthDate,
                                       final List<String> medications,
                                       final List<String> allergies) throws IOException, NoSuchDataException {

        MedicalRecord existantMedicalRecord = this.jsonFileDTO.getMedicalRecords().stream()
                .filter(medicalRecord -> medicalRecord.getLastName().equals(lastName))
                .filter(medicalRecord -> medicalRecord.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(NoSuchDataException::new);

        if (birthDate != null)
            existantMedicalRecord.setBirthDate(birthDate);
        if (medications != null)
            existantMedicalRecord.setMedications(medications);
        if (allergies != null)
            existantMedicalRecord.setAllergies(allergies);

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
                            final String lastName, boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        Logger.debug("Search JsonDatabase for Person with name : {} {}.", firstName, lastName);
        Person person;
        try {
            person = this.jsonFileDTO.getPersons().stream()
                    .filter(p -> p.getLastName().equals(lastName))
                    .filter(p -> p.getFirstName().equals(firstName))
                    .findFirst()
                    .orElseThrow(NoSuchDataException::new);
        } catch (NoSuchDataException e) {
            Logger.debug("No matched result.");
            throw e;
        }

        MedicalRecord medicalRecord;
        if (withMedicalRecord) {
            medicalRecord = this.jsonFileDTO.getMedicalRecords().stream()
                    .filter(m -> m.getLastName().equals(lastName))
                    .filter(m -> m.getFirstName().equals(firstName))
                    .findFirst()
                    .orElseThrow(() -> new NoMedicalRecordException(firstName, lastName));

            person = PersonFactory.buildPerson(person, medicalRecord);
        }

        Logger.debug("Matched Person.");

        return person;
    }


    public List<Person> getPersonFromAddress(final String address,
                                             boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        Logger.debug("Search JsonDatabase for Person with address : {}.", address);

        List<Person> persons = this.jsonFileDTO.getPersons().stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());

        if (persons.isEmpty()) {
            Logger.debug("No matched result.");
            throw new NoSuchDataException();
        }

        if (withMedicalRecord) {
            persons = getPersonsWithMedicalRecords(persons);
        }

        Logger.debug("Matched {} Person(s)", persons.size());
        return persons;
    }

    public List<Person> getPersonFromCity(final String city,
                                          boolean withMedicalRecord) throws NoSuchDataException, NoMedicalRecordException {
        Logger.debug("Search JsonDatabase for Person with city : {}.", city);

        List<Person> persons = this.jsonFileDTO.getPersons().stream()
                .filter(p -> p.getCity().equals(city))
                .collect(Collectors.toList());

        if (persons.isEmpty()) {
            Logger.debug("No matched result.");
            throw new NoSuchDataException();
        }

        if (withMedicalRecord) {
            persons = getPersonsWithMedicalRecords(persons);
        }

        Logger.debug("Matched {} Person(s)", persons.size());

        return persons;
    }

    private List<Person> getPersonsWithMedicalRecords(List<Person> persons) throws NoMedicalRecordException {

        List<Person> personsWithMedicalRecord = new ArrayList<>();
        for (Person person : persons) {
            personsWithMedicalRecord.add(PersonFactory.buildPerson(person,
                    this.jsonFileDTO.getMedicalRecords().stream()
                            .filter(m -> m.getFirstName().equals(person.getFirstName()))
                            .filter(m -> m.getLastName().equals(person.getLastName()))
                            .findFirst()
                            .orElseThrow(() -> new NoMedicalRecordException(person.getFirstName(), person.getLastName()))));
        }

        return personsWithMedicalRecord;
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
                                final String address,
                                final String city,
                                final String zip,
                                final String phone,
                                final String email) throws IOException, NoSuchDataException {

        Person existantPerson = this.jsonFileDTO.getPersons().stream()
                .filter(person -> person.getLastName().equals(lastName))
                .filter(person -> person.getFirstName().equals(firstName))
                .findFirst()
                .orElseThrow(NoSuchDataException::new);

        if (address != null)
            existantPerson.setAddress(address);
        if (city != null)
            existantPerson.setCity(city);
        if (zip != null)
            existantPerson.setZip(zip);
        if (phone != null)
            existantPerson.setPhone(phone);
        if (email != null)
            existantPerson.setEmail(email);

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
