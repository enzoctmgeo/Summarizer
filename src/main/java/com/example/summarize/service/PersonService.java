package com.example.summarize.service;

import com.example.summarize.dto.*;
import com.example.summarize.model.Person;
import com.example.summarize.repository.PersonRepository;
import jakarta.persistence.Tuple;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    // Organiza estados por maior número de pessoas acima de 50 anos
    public List<Object[]> findStateWithPeopleAgeGreaterEquals50() {
        return personRepository.findStateWithPeopleAgeGreaterEquals50();
    }

    // Organiza estados por maior número de pessoas abaixo de 20 anos
    public List<Object[]> findStateWithPeopleAgeLowerEquals20() {
        return personRepository.findStateWithPeopleAgeLowerEquals20();
    }

    // Contagem de estados com o maior número de pessoas
    public List<AgeCountDto> getMaxCountStates(List<AgeCountDto> states) {
        Long maxCount = Collections.max(states, Comparator.comparing(AgeCountDto::getTotal)).getTotal();

        List<AgeCountDto> maxCountStates = states.stream()
                .filter(state -> state.getTotal().equals(maxCount))
                .collect(Collectors.toList());

        return maxCountStates;
    }

    // Obtém dados de contagem de idade para acima de 50 e abaixo de 20 anos
    public AgeCountData getAgeCountData() {
        AgeCountData ageCountData = new AgeCountData();

        List<AgeCountDto> greaterEquals50 = findStateWithPeopleAgeGreaterEquals50().stream()
                .map(objects -> new AgeCountDto((String) objects[0], (Long) objects[1]))
                .collect(Collectors.toList());
        ageCountData.setGreaterEquals50(getMaxCountStates(greaterEquals50));

        List<AgeCountDto> lowerEquals20 = findStateWithPeopleAgeLowerEquals20().stream()
                .map(objects -> new AgeCountDto((String) objects[0], (Long) objects[1]))
                .collect(Collectors.toList());
        ageCountData.setLowerEquals20(getMaxCountStates(lowerEquals20));

        return ageCountData;
    }

    // Método para buscar a contagem de gênero para todos os estados
    public List<GenderByStateSortDto> findGenderCountByStates() {
        final List<Tuple> tuples = personRepository.findGenderCountByStates();
        final List<GenderByStateSortDto> genderByStateSortDtos = tuples.stream()
                .map(tuple -> new GenderByStateSortDto(
                        (String) tuple.get("state"),
                        (Long) tuple.get("male"),
                        (Long) tuple.get("female")
                )).collect(Collectors.toList());
        return genderByStateSortDtos;
    }

    // Método para buscar a contagem de gênero para todos os estados
    public List<Object[]> findGenderCountForAllStates() {
        return personRepository.findGenderCountForAllStates();
    }

    // Método auxiliar para obter os estados com a contagem máxima para uma faixa etária específica
    private List<AgeCountDto> getMaxAgeCountDtos(List<Object[]> results) {

        // Converte os resultados da consulta em DTOs
        List<AgeCountDto> ageCountDtos = results.stream()
                .map(objects -> new AgeCountDto((String) objects[0], (Long) objects[1]))
                .toList();

        // Encontra o valor máximo de contagem
        Long maxCount = ageCountDtos.stream()
                .max(Comparator.comparing(AgeCountDto::getTotal))
                .map(AgeCountDto::getTotal)
                .orElse(0L);

        // Filtra estados com o valor máximo de contagem
        return ageCountDtos.stream()
                .filter(dto -> dto.getTotal().equals(maxCount))
                .collect(Collectors.toList());
    }

    // Obtém dados de contagem de gênero mínimo e máximo por estado
    public Map<String, List<MinMaxStateGenderCountDto>> getStateGenderCountMinMaxData() {
        List<StateGenderCountDto> data = getStateGenderCountData();

        // Obtém estados com contagem mínima de pessoas do sexo masculino
        List<MinMaxStateGenderCountDto> minMale = getMinMaxStateGenderCountDtos(data, Gender.MALE, Operation.MIN);
        // Obtém estados com contagem mínima de pessoas do sexo feminino
        List<MinMaxStateGenderCountDto> minFemale = getMinMaxStateGenderCountDtos(data, Gender.FEMALE, Operation.MIN);
        // Obtém estados com contagem máxima de pessoas do sexo masculino
        List<MinMaxStateGenderCountDto> maxMale = getMinMaxStateGenderCountDtos(data, Gender.MALE, Operation.MAX);
        // Obtém estados com contagem máxima de pessoas do sexo feminino
        List<MinMaxStateGenderCountDto> maxFemale = getMinMaxStateGenderCountDtos(data, Gender.FEMALE, Operation.MAX);

        // Junta todas as listas
        List<MinMaxStateGenderCountDto> minList = new ArrayList<>();
        minList.addAll(minMale);
        minList.addAll(minFemale);

        List<MinMaxStateGenderCountDto> maxList = new ArrayList<>();
        maxList.addAll(maxMale);
        maxList.addAll(maxFemale);

        return Map.of("min", minList, "max", maxList);
    }

    // Obter dados de contagem de gênero por estado
    private List<StateGenderCountDto> getStateGenderCountData() {
        return personRepository.findGenderCountForAllStates().stream()
                .map(objects -> new StateGenderCountDto((String) objects[0], (Long) objects[1], (Long) objects[2]))
                .collect(Collectors.toList());
    }

    // Método para obter a lista de DTOs de contagem mínima ou máxima por gênero
    private List<MinMaxStateGenderCountDto> getMinMaxStateGenderCountDtos(List<StateGenderCountDto> data, Gender gender, Operation operation) {
        long targetValue = operation == Operation.MAX
                ? data.stream().filter(dto -> getGenderCount(dto, gender) != 0).map(dto -> getGenderCount(dto, gender)).max(Comparator.naturalOrder()).orElse(0L)
                : data.stream().filter(dto -> getGenderCount(dto, gender) != 0).map(dto -> getGenderCount(dto, gender)).min(Comparator.naturalOrder()).orElse(0L);
        // Filtra e mapeia estados com a contagem alvo
        return data.stream()
                .filter(dto -> getGenderCount(dto, gender) == targetValue)
                .map(dto -> new MinMaxStateGenderCountDto(gender.name().toLowerCase(), dto.getState(), targetValue))
                .collect(Collectors.toList());
    }

    // Obter a contagem de gênero (masculino ou feminino) do DTO
    private Long getGenderCount(StateGenderCountDto dto, Gender gender) {
        return (gender == Gender.MALE) ? dto.getMale() : dto.getFemale();
    }

    // Comparador de contagem de gênero por estado
    private Comparator<StateGenderCountDto> comparatorByGender(Gender gender) {
        return (gender == Gender.MALE) ? Comparator.comparing(StateGenderCountDto::getMale) : Comparator.comparing(StateGenderCountDto::getFemale);
    }

    // Enumeração para representar gêneros
    private enum Gender {
        MALE, FEMALE
    }

    // Enumeração para representar operações (mínimo ou máximo)
    private enum Operation {
        MIN, MAX
    }
}




