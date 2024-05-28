package com.example.summarize.integration;

import com.example.summarize.controller.PersonController;
import com.example.summarize.dto.AgeCountData;
import com.example.summarize.dto.AgeCountDto;
import com.example.summarize.dto.StateGenderCountDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

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

    /**
     * Deve conferir que o corpo do endpoint possui as propriedades esperadas
     * <p>
     */
    @Test
    public void verTodasPessoas() {
        /* Act */
        ResponseEntity<String> response = restTemplate.getForEntity
                ("http://localhost:" + port + "/api/persons", String.class);

        /* Assert */
        assertThat(response.getBody()).contains("id", "gender", "title", "givenName", "middleInitial",
                "surname", "state", "email", "latitude", "longitude", "age");

    }

    /**
     * Deve conferir que o corpo do endpoint possui as propriedades esperadas
     * <p>
     */
    @Test
    public void verMinMaxGenerosPorEstado() {
        /* Act */
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/stateGenderCountMinMaxData", String.class);

        /* Assert */
        assertThat(response.getBody()).contains("gender", "male", "female", "max", "min", "SP", "485", "472", "AC", "2", "RR", "SE", "1");
    }

    /**
     * Deve conferir que o corpo do endpoint possui as propriedades esperadas
     * <p>
     */
    @Test
    public void verMaiorQueCinquentaMenorQueVinte() throws Exception {
        /* Act */
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/ageCountData", String.class);
        String body = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        AgeCountData ageCountData = objectMapper.readValue(body, AgeCountData.class);

        verifyAgeCount(ageCountData.getGreaterEquals50(), "SP", 530L);
        verifyAgeCount(ageCountData.getLowerEquals20(), "SP", 23L);
    }

    private void verifyAgeCount(List<AgeCountDto> ageCounts, String state, Long expectedTotal) {
        Optional<AgeCountDto> optionalAgeCount = ageCounts.stream()
                .filter(ac -> ac.getState().equals(state))
                .findFirst();

        /* Assert */
        assertThat(optionalAgeCount).isPresent();
        AgeCountDto ageCount = optionalAgeCount.get();
        assertThat(ageCount.getTotal()).isEqualTo(expectedTotal);
    }

    /**
     * Deve retornar a quantidade de valores esperados de gênero para cada estado
     * <p>
     */
    @Test
    public void getStateGenderCounts() throws Exception {
        /* Act */
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/genderCountByStates", String.class);

        /* Assert */
        String body = response.getBody();

        // Usando Jackson para converter a resposta JSON em uma lista de objetos
        ObjectMapper objectMapper = new ObjectMapper();
        List<StateGenderCountDto> genderCounts = objectMapper.readValue(body, new TypeReference<>() {
        });

        // Verificar valores específicos para cada estado
        verifyGenderCount(genderCounts, "AC", 2, 3);
        verifyGenderCount(genderCounts, "AL", 10, 14);
        verifyGenderCount(genderCounts, "AM", 15, 16);
        verifyGenderCount(genderCounts, "AP", 3, 6);
        verifyGenderCount(genderCounts, "BA", 59, 56);
        verifyGenderCount(genderCounts, "CE", 42, 39);
        verifyGenderCount(genderCounts, "DF", 52, 56);
        verifyGenderCount(genderCounts, "ES", 44, 46);
        verifyGenderCount(genderCounts, "GO", 79, 69);
        verifyGenderCount(genderCounts, "MA", 10, 17);
        verifyGenderCount(genderCounts, "MG", 138, 115);
        verifyGenderCount(genderCounts, "MS", 26, 21);
        verifyGenderCount(genderCounts, "MT", 20, 15);
        verifyGenderCount(genderCounts, "PA", 36, 33);
        verifyGenderCount(genderCounts, "PB", 22, 20);
        verifyGenderCount(genderCounts, "PE", 65, 73);
        verifyGenderCount(genderCounts, "PI", 12, 11);
        verifyGenderCount(genderCounts, "PR", 81, 97);
        verifyGenderCount(genderCounts, "RJ", 131, 142);
        verifyGenderCount(genderCounts, "RN", 23, 11);
        verifyGenderCount(genderCounts, "RO", 9, 6);
        verifyGenderCount(genderCounts, "RR", 2, 1);
        verifyGenderCount(genderCounts, "RS", 68, 80);
        verifyGenderCount(genderCounts, "SC", 63, 46);
        verifyGenderCount(genderCounts, "SE", 6, 1);
        verifyGenderCount(genderCounts, "SP", 485, 472);
        verifyGenderCount(genderCounts, "TO", 13, 18);
    }

    private void verifyGenderCount(List<StateGenderCountDto> genderCounts, String state, int expectedMale, int expectedFemale) {
        Optional<StateGenderCountDto> optionalGenderCount = genderCounts.stream()
                .filter(gc -> gc.getState().equals(state))
                .findFirst();

        assertThat(optionalGenderCount).isPresent();
        StateGenderCountDto genderCount = optionalGenderCount.get();
        assertThat(genderCount.getMale()).isEqualTo(expectedMale);
        assertThat(genderCount.getFemale()).isEqualTo(expectedFemale);
    }
}

