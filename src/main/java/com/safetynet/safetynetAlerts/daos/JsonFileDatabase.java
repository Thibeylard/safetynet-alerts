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
     * @param mapper      Autowired ObjectMapper Singleton
     * @param src         Database Json File path injection
     * @throws IOException if Json File related error occurs
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

    /**
     * After any data modification, reset Json File database by injecting all three lists (Persons,Firestations and MedicalRecords) as Json.
     *
     * @return True if operation succeed
     * @throws IOException if Json File related error occurs
     */
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

    /**
     * Firestation accessor based on address attribute.
     *
     * @param address address to search for
     * @return Firestation object
     * @throws NoSuchDataException if address not found
     */
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

    /**
     * Firestation accessor based on stationNumber attribute.
     *
     * @param number stationNumber to search for
     * @return Firestation List
     * @throws NoSuchDataException if number not found
     */
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

    /**
     * Firestation addition (address attribute used as identifier).
     *
     * @param address Value to set for eponym attribute.
     * @param number  Value to set for eponym attribute.
     * @return True if operation succeed
     * @throws IOException                  if Json File related error occurs
     * @throws IllegalDataOverrideException if Firestation with same address already exists
     */
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

    /**
     * Firestation stationNumber update based on address.
     *
     * @param address address to search for
     * @param number  new value to set
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if address not found
     */
    public boolean updateFirestation(final String address, final int number) throws IOException, NoSuchDataException {

        this.jsonFileDTO.getFirestations().stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .findFirst()
                .orElseThrow(NoSuchDataException::new)
                .setStation(number);

        return writeDataToJsonFile();
    }

    /**
     * Firestation deletion based on address
     *
     * @param address value to search for
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if address not found
     */
    public boolean deleteFirestation(final String address) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getAddress().equals(address))) {
            throw new NoSuchDataException();
        }
        return writeDataToJsonFile();
    }

    /**
     * Firestation multiple deletion based on stationNumber
     *
     * @param number value to search for among all Firestations
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if number not found
     */
    public boolean deleteFirestation(final int number) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getFirestations().removeIf(firestation -> firestation.getStation() == number)) {
            throw new NoSuchDataException();
        }
        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- MEDICALRECORD

    /**
     * MedicalRecord accessor based on firstName and lastName attributes couple.
     *
     * @param firstName firstName to search for
     * @param lastName  lastName to search for
     * @return MedicalRecord object
     * @throws NoSuchDataException if couple not found
     */
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

    /**
     * MedicalRecord addition (firstName and lastName attributes couple used as identifier)
     *
     * @param firstName   Value to set for eponym attribute.
     * @param lastName    Value to set for eponym attribute.
     * @param birthDate   Value to set for eponym attribute.
     * @param medications Value to set for eponym attribute.
     * @param allergies   Value to set for eponym attribute.
     * @return True if operation succeed
     * @throws IOException                  if Json File related error occurs
     * @throws IllegalDataOverrideException if MedicalRecord with same firstName/lastName already exists
     */
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

    /**
     * MedicalRecord birthDate, medications and/or allergies update based on firstName and lastName attributes couple.
     *
     * @param firstName   firstName to search for
     * @param lastName    lastName to search for
     * @param birthDate   new value to set (null value equals no modification)
     * @param medications new value to set (null value equals no modification)
     * @param allergies   new value to set (null value equals no modification)
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if MedicalRecord with firstName/lastName not found
     */
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

    /**
     * MedicalRecord deletion based on firstName and lastName attributes couple.
     *
     * @param firstName firstName to search for
     * @param lastName  lastName to search for
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if MedicalRecord with firstName/lastName not found
     */
    public boolean deleteMedicalRecord(final String firstName,
                                       final String lastName) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getMedicalRecords().removeIf(medicalRecord ->
                medicalRecord.getFirstName().equals(firstName) && medicalRecord.getLastName().equals(lastName))) {
            throw new NoSuchDataException();
        }

        return writeDataToJsonFile();
    }

    //    ---------------------------------------------------------------------------------------- PERSON

    /**
     * Person accessor based on firstName and lastName attributes couple.
     *
     * @param firstName         firstName to search for
     * @param lastName          lastName to search for
     * @param withMedicalRecord True if Person must be build with its MedicalRecord
     * @return Person object
     * @throws NoSuchDataException      if Person with firstName/lastName not found
     * @throws NoMedicalRecordException if Person MedicalRecord is not in database
     */
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

    /**
     * Person accessor based on address attribute.
     *
     * @param address           address to search for
     * @param withMedicalRecord True if Person must be build with its MedicalRecord
     * @return Person List
     * @throws NoSuchDataException      if address is not found among Persons
     * @throws NoMedicalRecordException if any Person MedicalRecord is not in database
     */
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

    /**
     * Person accessor based on city attribute.
     *
     * @param city              city to search for
     * @param withMedicalRecord True if Person must be build with its MedicalRecord
     * @return Person List
     * @throws NoSuchDataException      if address is not found among Persons
     * @throws NoMedicalRecordException if any Person MedicalRecord is not in database
     */
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

    /**
     * Based on Person List, return same List with each Person having medicalRecord and age attributes set.
     *
     * @param persons List of Person for whom to get according MedicalRecord
     * @return Person List
     * @throws NoMedicalRecordException if any Person MedicalRecord is not in database
     */
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

    /**
     * Person addition (firstName and lastName attributes couple used as identifier).
     *
     * @param firstName Value to set for eponym attribute.
     * @param lastName  Value to set for eponym attribute.
     * @param address   Value to set for eponym attribute.
     * @param city      Value to set for eponym attribute.
     * @param zip       Value to set for eponym attribute.
     * @param phone     Value to set for eponym attribute.
     * @param email     Value to set for eponym attribute.
     * @return True if operation succeed
     * @throws IOException                  if Json File related error occurs
     * @throws IllegalDataOverrideException if Person with same firstName/lastName already exists
     */
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

    /**
     * Person address, city, zip, phone or/and email update based on firstName and lastName attributes couple.
     *
     * @param firstName value to search for
     * @param lastName  value to search for
     * @param address   new value to set (null value equals no modification)
     * @param city      new value to set (null value equals no modification)
     * @param zip       new value to set (null value equals no modification)
     * @param phone     new value to set (null value equals no modification)
     * @param email     new value to set (null value equals no modification)
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if Person with firstName/lastName is not found
     */
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

    /**
     * Person deletion based on firstName and lastName attributes couple.
     *
     * @param firstName value to search for
     * @param lastName  value to search for
     * @return True if operation succeed
     * @throws IOException         if Json File related error occurs
     * @throws NoSuchDataException if Person with firstName/lastName is not found
     */
    public boolean deletePerson(final String firstName,
                                final String lastName) throws IOException, NoSuchDataException {
        if (!jsonFileDTO.getPersons().removeIf(person ->
                person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))) {
            throw new NoSuchDataException();
        }

        return writeDataToJsonFile();
    }

}
