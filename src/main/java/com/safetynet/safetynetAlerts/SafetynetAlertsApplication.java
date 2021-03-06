package com.safetynet.safetynetAlerts;

import com.fasterxml.jackson.core.JsonFactory;
import com.safetynet.safetynetAlerts.services.ClockService;
import com.safetynet.safetynetAlerts.services.ClockServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SafetynetAlertsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetynetAlertsApplication.class, args);
    }

    /**
     * Enables httptrace endpoint (disabled by default from SpringBoot 2.2.0).
     *
     * @return InMemoryHttpTraceRepository object
     */
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public JsonFactory getJsonFactory() {
        return new JsonFactory();
    }

    @Bean
    public ClockService getClock() {
        return new ClockServiceImpl();
    }

}
