package com.safetynet.safetynetAlerts.integration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetAlerts.dtos.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("AlertsController integration tests :")
public class AlertsControllerIT {

    // URLS
    private final static String firestationURL = "/firestation?stationNumber={stationNumber}";
    private final static String childAlertURL = "/childAlert?address={address}";
    private final static String phoneAlertURL = "/phoneAlert?firestation={firestation}";
    private final static String fireURL = "/fire?address={address}";
    private final static String floodURL = "/flood/stations?stations={stations}";
    private final static String floodParamURL = "&stations={stations}";
    private final static String personInfoURL = "/personInfo?firstName={firstName}&lastName={lastName}";
    private final static String communityEmailURL = "/communityEmail?city={city}";
    private static File data;
    private static JsonFactory factory;
    private static JsonFileDatabaseDTO jsonFileDTO;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public static void saveData(@Value("${jsondatabase.src}") String src) throws IOException {
        data = new File(src);
        factory = new JsonFactory().setCodec(new ObjectMapper());
        JsonParser parser = factory.createParser(AlertsControllerIT.data);
        jsonFileDTO = parser.readValueAs(JsonFileDatabaseDTO.class);
    }


    @AfterAll
    public static void resetData() {
        try {
            JsonGenerator generator = factory.createGenerator(data, JsonEncoding.UTF8);
            generator.writeStartObject();
            generator.writeObjectField("persons", jsonFileDTO.getPersons());
            generator.writeObjectField("firestations", jsonFileDTO.getFirestations());
            generator.writeObjectField("medicalrecords", jsonFileDTO.getMedicalRecords());
            generator.writeEndObject();
            generator.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Firestation successful")
    void Given_firestationNumber_When_firestationURL_Then_getAccordingDTO() {
        ResponseEntity<URLFirestationDTO> response = restTemplate.getForEntity(firestationURL, URLFirestationDTO.class, 2);
        assertThat(response.getBody())
                .isNotNull();
        assertThat(response.getBody().getInhabitants().size())
                .isEqualTo(response.getBody().getAdults() + response.getBody().getChildren());
    }

    @Test
    @DisplayName("Firestation not found")
    void Given_absentFirestationNumber_When_firestationURL_Then_getNull() {
        ResponseEntity<URLFirestationDTO> response = restTemplate.getForEntity(firestationURL, URLFirestationDTO.class, 0);
        assertThat(response.getBody())
                .isNull();
    }

    @Test
    @DisplayName("ChildAlert successful")
    void Given_address_When_childAlertURL_Then_getAccordingDTO() {
        ResponseEntity<URLChildAlertDTO> response = restTemplate.getForEntity(childAlertURL, URLChildAlertDTO.class, "1509 Culver St");
        assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("PhoneAlert successful")
    void Given_firestationNumber_When_phoneAlertURL_Then_getDTO() {
        ResponseEntity<URLPhoneAlertDTO> response = restTemplate.getForEntity(phoneAlertURL, URLPhoneAlertDTO.class, 2);
        assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("Fire successful")
    void Given_address_When_fireURL_Then_getAccordingDTO() {
        ResponseEntity<URLFireDTO> response = restTemplate.getForEntity(fireURL, URLFireDTO.class, "892 Downing Ct");
        assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("Flood successful")
    void Given_firestationNumbers_When_floodURL_Then_getAccordingDTO() {
        ResponseEntity<URLFloodDTO> response = restTemplate.getForEntity(floodURL + floodParamURL, URLFloodDTO.class, 2, 3);
        assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("Person Info successful")
    void Given_completeName_When_personInfoURL_Then_getAccordingDTO() {
        ResponseEntity<URLPersonInfoDTO> response = restTemplate.getForEntity(personInfoURL, URLPersonInfoDTO.class, "Jamie", "Peters");
        assertThat(response.getBody())
                .isNotNull();
    }

    @Test
    @DisplayName("Community Email successful")
    void Given_city_When_communityEmailURL_Then_getAccordingDTO() {
        ResponseEntity<URLCommunityEmailDTO> response = restTemplate.getForEntity(communityEmailURL, URLCommunityEmailDTO.class, "Culver");
        assertThat(response.getBody())
                .isNotNull();
    }
}