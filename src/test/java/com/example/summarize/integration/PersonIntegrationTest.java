package com.example.summarize.integration;

import com.example.summarize.controller.PersonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersonIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PersonController controller;

    @Test
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    public void getAllPersons() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/persons", String.class);
        assertThat(response.getBody()).contains("name");
    }

    @Test
    public void getMaxMinPeopleByGender() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/maxmin-gender", String.class);
        assertThat(response.getBody()).contains("male", "female", "max", "min");
    }

    @Test
    public void getMaxPeopleByAge() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/max-ages", String.class);
        assertThat(response.getBody()).contains("above50", "below20");
    }

    @Test
    public void getStateGenderCounts() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/state-gender-counts", String.class);
        assertThat(response.getBody()).contains("state", "gender", "count");
    }
}
