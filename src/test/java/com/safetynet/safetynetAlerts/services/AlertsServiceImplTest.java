package com.safetynet.safetynetAlerts.services;

import com.safetynet.safetynetAlerts.daos.FirestationDAO;
import com.safetynet.safetynetAlerts.daos.MedicalRecordDAO;
import com.safetynet.safetynetAlerts.daos.PersonDAO;
import com.safetynet.safetynetAlerts.dtos.*;
import com.safetynet.safetynetAlerts.exceptions.NoMedicalRecordException;
import com.safetynet.safetynetAlerts.exceptions.NoSuchDataException;
import com.safetynet.safetynetAlerts.factories.FirestationFactory;
import com.safetynet.safetynetAlerts.factories.MedicalRecordFactory;
import com.safetynet.safetynetAlerts.factories.PersonFactory;
import com.safetynet.safetynetAlerts.factories.enums.Addresses;
import com.safetynet.safetynetAlerts.models.Firestation;
import com.safetynet.safetynetAlerts.models.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("AlertsService Tests on :")
class AlertsServiceImplTest {

    @MockBean
    private FirestationDAO mockFirestationDAO;

    @MockBean
    private MedicalRecordDAO mockMedicalRecordDAO;

    @MockBean
    private PersonDAO mockPersonDAO;

    @Autowired
    private AlertsService alertsService;


    //    ---------------------------------------------------------------------------getURLFirestationDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLFirestationDTO()")
    class getURLFirestationDTOTestMethods {

        @Test
        void Given_stationNumber1_When_getURLFirestationDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            //Three different addresses under Station 1
            List<Firestation> stations = FirestationFactory.getFirestations(3, Optional.of(1));
            List<Person> inhabitants = new ArrayList<>();

            // First address : 2 adults, 1 children
            inhabitants.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            inhabitants.addAll(PersonFactory.createChildren(1, Optional.empty(), Optional.empty()));

            // Second address : 1 adult
            inhabitants.addAll(PersonFactory.createAdults(1, Optional.empty(), Optional.empty()));

            //Third address : 2 adults, 3 children
            inhabitants.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            inhabitants.addAll(PersonFactory.createChildren(3, Optional.empty(), Optional.empty()));

            // Create list of persons formatted as dto
            List<PersonAddressPhoneDTO> inhabitantsFormatted = new ArrayList<>();
            inhabitants.forEach(person -> inhabitantsFormatted.add(new PersonAddressPhoneDTO(person)));

            // Adults 5, Children 4.
            URLFirestationDTO responseDTO = new URLFirestationDTO(5, 4, inhabitantsFormatted);

            doReturn(stations).when(mockFirestationDAO).getFirestations(1);
            doReturn(inhabitants).when(mockPersonDAO).getFromAddress(any(List.class), anyBoolean());

            assertThat(alertsService.getURLFirestationDTO(1))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);
        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLFirestationDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO).getFirestations(1);

            assertThat(alertsService.getURLFirestationDTO(1)) // NoSuchDataException on Firestation request
                    .isNull();

            //Four different addresses under Station 1
            List<Firestation> stations = FirestationFactory.getFirestations(4, Optional.of(1));

            doReturn(stations).when(mockFirestationDAO).getFirestations(1);
            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromAddress(any(List.class), anyBoolean());

            assertThat(alertsService.getURLFirestationDTO(1)) // NoSuchDataException on Person request
                    .isNull();
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFirestationDTO_Then_throwIOException() throws IOException, NoSuchDataException {
            doThrow(new IOException()).when(mockFirestationDAO).getFirestations(1);

            assertThrows(IOException.class, () -> alertsService.getURLFirestationDTO(1));
        }
    }

    //    ---------------------------------------------------------------------------getURLChildAlertDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLChildAlertDTO()")
    class getURLChildAlertDTOTestMethods {
        @Test
        void Given_anyAddress_When_getURLChildAlertDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            List<Person> children = PersonFactory.createChildren(2, Optional.empty(), Optional.of(Addresses.APPLEGATE));
            List<Person> familyMembers = PersonFactory.createAdults(3, Optional.empty(), Optional.of(Addresses.APPLEGATE));
            List<Person> inhabitants = new ArrayList<>();

            inhabitants.addAll(children);
            inhabitants.addAll(familyMembers);

            doReturn(inhabitants).when(mockPersonDAO).getFromAddress(Addresses.APPLEGATE.getName(), true);

            List<ChildDTO> childrenDTO = new ArrayList<>();
            List<ChildFamilyMemberDTO> familyMemberDTO = new ArrayList<>();

            for (Person child : children) {
                childrenDTO.add(new ChildDTO(child));
            }

            familyMembers.forEach(member -> familyMemberDTO.add(new ChildFamilyMemberDTO(member)));

            URLChildAlertDTO responseDTO = new URLChildAlertDTO(childrenDTO, familyMemberDTO);

            assertThat(alertsService.getURLChildAlertDTO(Addresses.APPLEGATE.getName()))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);
        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLChildAlertDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromAddress(Addresses.APPLEGATE.getName(), true);

            assertThat(alertsService.getURLChildAlertDTO(Addresses.APPLEGATE.getName()))
                    .isNull();
        }

        @Test
        void Given_noChildAtAddress_When_getURLChildAlertDTO_Then_emptyObject() throws IOException, NoSuchDataException, NoMedicalRecordException {
            List<Person> familyMembers = PersonFactory.createAdults(3, Optional.empty(), Optional.of(Addresses.APPLEGATE));

            doReturn(familyMembers).when(mockPersonDAO).getFromAddress(Addresses.APPLEGATE.getName(), true);

            List<ChildDTO> childrenDTO = new ArrayList<>();
            List<ChildFamilyMemberDTO> familyMemberDTO = new ArrayList<>();

            URLChildAlertDTO responseDTO = new URLChildAlertDTO(childrenDTO, familyMemberDTO);

            assertThat(alertsService.getURLChildAlertDTO(Addresses.APPLEGATE.getName()))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);
        }

        @Test
        void Given_IOExceptionThrown_When_getURLChildAlertDTO_Then_throwIOException() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new IOException()).when(mockPersonDAO).getFromAddress(Addresses.APPLEGATE.getName(), true);

            assertThrows(IOException.class, () -> alertsService.getURLChildAlertDTO(Addresses.APPLEGATE.getName()));
        }
    }

    //    ---------------------------------------------------------------------------getURLPhoneAlertDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLPhoneAlertDTO()")
    class getURLPhoneAlertDTOTestMethods {

        @Test
        void Given_stationNumber1_When_getURLPhoneAlertDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            //Three different addresses under Station 1
            List<Firestation> stations = FirestationFactory.getFirestations(3, Optional.of(1));
            List<Person> inhabitants = new ArrayList<>();

            inhabitants.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            inhabitants.addAll(PersonFactory.createChildren(1, Optional.empty(), Optional.empty()));

            inhabitants.addAll(PersonFactory.createAdults(1, Optional.empty(), Optional.empty()));

            inhabitants.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            inhabitants.addAll(PersonFactory.createChildren(3, Optional.empty(), Optional.empty()));

            // Create list of persons formatted as dto
            List<PersonPhoneDTO> inhabitantsFormatted = new ArrayList<>();
            inhabitants.forEach(person -> inhabitantsFormatted.add(new PersonPhoneDTO(person)));

            // Adults 5, Children 4.
            URLPhoneAlertDTO responseDTO = new URLPhoneAlertDTO(inhabitantsFormatted);

            doReturn(stations).when(mockFirestationDAO).getFirestations(1);
            doReturn(inhabitants).when(mockPersonDAO).getFromAddress(any(List.class), anyBoolean());

            assertThat(alertsService.getURLPhoneAlertDTO(1))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);
        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLPhoneAlertDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO).getFirestations(1);

            assertThat(alertsService.getURLPhoneAlertDTO(1)) // NoSuchDataException on Firestation request
                    .isNull();

            List<Firestation> stations = FirestationFactory.getFirestations(1, Optional.of(1));

            doReturn(stations).when(mockFirestationDAO).getFirestations(1);
            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromAddress(any(List.class), anyBoolean());

            assertThat(alertsService.getURLPhoneAlertDTO(1)) // NoSuchDataException on Person request
                    .isNull();
        }


        @Test
        void Given_IOExceptionThrown_When_getURLPhoneAlertDTO_Then_throwIOException() throws IOException, NoSuchDataException {
            doThrow(new IOException()).when(mockFirestationDAO).getFirestations(1);

            assertThrows(IOException.class, () -> alertsService.getURLPhoneAlertDTO(1));
        }
    }

    //    ---------------------------------------------------------------------------getURLFireDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLFireDTO()")
    class getURLFireDTOTestMethods {

        @Test
        void Given_address_When_getURLFireDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            Firestation firestation = FirestationFactory.getFirestation(Optional.empty(), Optional.empty());
            List<Person> persons = new ArrayList<>();

            persons.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            persons.addAll(PersonFactory.createChildren(2, Optional.empty(), Optional.empty()));

            List<EndangeredPersonDTO> personsFormatted = new ArrayList<>();
            for (Person person : persons) {
                personsFormatted.add(new EndangeredPersonDTO(person));
            }
            URLFireDTO responseDTO = new URLFireDTO(firestation.getStation(), personsFormatted);

            doReturn(firestation).when(mockFirestationDAO).getFirestation(anyString());
            doReturn(persons).when(mockPersonDAO).getFromAddress(anyString(), anyBoolean());

            assertThat(alertsService.getURLFireDTO(Addresses.CIRCLE.getName()))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);
        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLFireDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO).getFirestation(anyString());

            assertThat(alertsService.getURLFireDTO(Addresses.BRICKYARD.getName())) // NoSuchDataException on Firestation request
                    .isNull();

            List<Firestation> stations = FirestationFactory.getFirestations(1, Optional.of(1));

            doReturn(stations).when(mockFirestationDAO).getFirestations(1);
            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromAddress(anyString(), anyBoolean());

            assertThat(alertsService.getURLFireDTO(Addresses.BRICKYARD.getName())) // NoSuchDataException on Person request
                    .isNull();
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFireDTO_Then_throwIOException() throws IOException, NoSuchDataException {
            doThrow(new IOException()).when(mockFirestationDAO).getFirestation(anyString());

            assertThrows(IOException.class, () -> alertsService.getURLFireDTO(Addresses.BRICKYARD.getName()));
        }

    }

    //    ---------------------------------------------------------------------------getURLFloodDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLFloodDTO()")
    class getURLFloodDTOTestMethods {
        @Test
        void Given_stationNumberList_When_getURLFloodDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {

            List<Firestation> firestations1 = FirestationFactory.getFirestations(1, Optional.of(1));
            List<Firestation> firestations2 = FirestationFactory.getFirestations(1, Optional.of(2));

            List<Person> persons1 = new ArrayList<>();
            persons1.addAll(PersonFactory.createAdults(2, Optional.empty(), Optional.empty()));
            persons1.addAll(PersonFactory.createChildren(2, Optional.empty(), Optional.empty()));
            List<EndangeredPersonDTO> persons1DTO = new ArrayList<>();

            for (Person person : persons1) {
                persons1DTO.add(new EndangeredPersonDTO(person));
            }

            List<Person> persons2 = new ArrayList<>();
            persons2.addAll(PersonFactory.createAdults(4, Optional.empty(), Optional.empty()));
            persons2.addAll(PersonFactory.createChildren(1, Optional.empty(), Optional.empty()));

            List<EndangeredPersonDTO> persons2DTO = new ArrayList<>();

            for (Person person : persons2) {
                persons2DTO.add(new EndangeredPersonDTO(person));
            }


            doReturn(firestations1).when(mockFirestationDAO).getFirestations(1);
            doReturn(firestations2).when(mockFirestationDAO).getFirestations(2);

            doReturn(persons1).when(mockPersonDAO).getFromAddress(firestations1.get(0).getAddress(), true);
            doReturn(persons2).when(mockPersonDAO).getFromAddress(firestations2.get(0).getAddress(), true);


            Map<String, List<EndangeredPersonDTO>> personAddressMap = new HashMap<>();
            personAddressMap.put(firestations1.get(0).getAddress(), persons1DTO);
            personAddressMap.put(firestations2.get(0).getAddress(), persons2DTO);

            URLFloodDTO responseDTO = new URLFloodDTO(personAddressMap);

            assertThat(alertsService.getURLFloodDTO(List.of(1, 2)))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);

        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLFloodDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            doThrow(new NoSuchDataException()).when(mockFirestationDAO).getFirestations(anyInt());

            assertThat(alertsService.getURLFloodDTO(List.of(1, 2))) // NoSuchDataException on Firestation request
                    .isNull();

            List<Firestation> firestations1 = FirestationFactory.getFirestations(1, Optional.of(1));
            List<Firestation> firestations2 = FirestationFactory.getFirestations(1, Optional.of(2));

            doReturn(firestations1).when(mockFirestationDAO).getFirestations(1);
            doReturn(firestations2).when(mockFirestationDAO).getFirestations(2);

            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromAddress(anyString(), anyBoolean());

            assertThat(alertsService.getURLFloodDTO(List.of(1, 2))) // NoSuchDataException on Person request
                    .isNull();
        }

        @Test
        void Given_IOExceptionThrown_When_getURLFloodDTO_Then_throwIOException() throws IOException, NoSuchDataException {
            doThrow(new IOException()).when(mockFirestationDAO).getFirestations(anyInt());

            assertThrows(IOException.class, () -> alertsService.getURLFloodDTO(List.of(1, 2)));
        }
    }

    //    ---------------------------------------------------------------------------getURLPersonInfoDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLPersonInfoDTO()")
    class getURLPersonInfoTestMethods {
        @Test
        void Given_personFirstNameAndLastName_When_getURLPersonInfoDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            Person target = PersonFactory.createPerson(
                    Optional.of("John"),
                    Optional.of("Smith"),
                    Optional.empty(),
                    Optional.of(MedicalRecordFactory.getMedicalRecord(
                            Optional.of("John"),
                            Optional.of("Smith"),
                            false)));

            List<Person> persons = new ArrayList<>(PersonFactory.createAdults(5, Optional.empty(), Optional.empty()));
            persons.add(target);

            doReturn(target).when(mockPersonDAO).getFromName(target.getFirstName(), target.getLastName(), true);

            URLPersonInfoDTO responseDTO = new URLPersonInfoDTO(target);

            assertThat(alertsService.getURLPersonInfoDTO(target.getFirstName(), target.getLastName()))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);

        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLPersonInfoDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            Person target = PersonFactory.createPerson(
                    Optional.of("John"),
                    Optional.of("Smith"),
                    Optional.empty(),
                    Optional.of(MedicalRecordFactory.getMedicalRecord(
                            Optional.of("John"),
                            Optional.of("Smith"),
                            false)));

            doThrow(new NoSuchDataException()).when(mockPersonDAO).getFromName(target.getFirstName(), target.getLastName(), true);

            assertThat(alertsService.getURLPersonInfoDTO(target.getFirstName(), target.getLastName()))
                    .isNull();
        }

        @Test
        void Given_IOExceptionThrown_When_getURLPersonInfoDTO_Then_throwIOException() throws IOException, NoSuchDataException, NoMedicalRecordException {
            Person target = PersonFactory.createPerson(
                    Optional.of("John"),
                    Optional.of("Smith"),
                    Optional.empty(),
                    Optional.of(MedicalRecordFactory.getMedicalRecord(
                            Optional.of("John"),
                            Optional.of("Smith"),
                            false)));

            doThrow(new IOException()).when(mockPersonDAO).getFromName(target.getFirstName(), target.getLastName(), true);

            assertThrows(IOException.class, () -> alertsService.getURLPersonInfoDTO(target.getFirstName(), target.getLastName()));
        }
    }

    //    ---------------------------------------------------------------------------getURLCommunityEmailDTO()
    //    -------------------------------------------------------------------------------------
    @Nested
    @DisplayName("getURLCommunityEmailDTO()")
    class getURLCommunityEmailDTOTestMethods {
        @Test
        void Given_personFirstNameAndLastName_When_getURLPersonInfoDTO_Then_returnCorrespondingDTO() throws IOException, NoSuchDataException, NoMedicalRecordException {
            String city = Addresses.GOLFCOURT.getCity().getName();
            List<Person> citizens = new ArrayList<>(PersonFactory.createAdults(10, Optional.empty(), Optional.of(Addresses.GOLFCOURT)));

            doReturn(citizens).when(mockPersonDAO).getCommunity(city, false);

            URLCommunityEmailDTO responseDTO = new URLCommunityEmailDTO(citizens);

            assertThat(alertsService.getURLCommunityEmailDTO(city))
                    .isNotNull()
                    .usingRecursiveComparison()
                    .isEqualTo(responseDTO);

        }

        @Test
        void Given_NoSuchDataExceptionThrown_When_getURLPersonInfoDTO_Then_returnNull() throws IOException, NoSuchDataException, NoMedicalRecordException {
            String city = Addresses.GOLFCOURT.getCity().getName();

            doThrow(new NoSuchDataException()).when(mockPersonDAO).getCommunity(city, false);

            assertThat(alertsService.getURLCommunityEmailDTO(city))
                    .isNull();
        }

        @Test
        void Given_IOExceptionThrown_When_getURLPersonInfoDTO_Then_throwIOException() throws IOException, NoSuchDataException, NoMedicalRecordException {
            String city = Addresses.GOLFCOURT.getCity().getName();
            doThrow(new IOException()).when(mockPersonDAO).getCommunity(city, false);

            assertThrows(IOException.class, () -> alertsService.getURLCommunityEmailDTO(city));
        }
    }

}
