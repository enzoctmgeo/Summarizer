package com.example.summarize.controller;

import com.example.summarize.dto.AgeCountData;
import com.example.summarize.dto.GenderByStateSortDto;
import com.example.summarize.dto.MinMaxStateGenderCountDto;
import com.example.summarize.dto.StateGenderCountDto;
import com.example.summarize.model.Person;
import com.example.summarize.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Get persons
    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }

    // Post persons
    @PostMapping("/persons")
    public Person savePerson(@RequestBody Person person) {
        return personService.save(person);
    }

    // Put persons
    @PutMapping("/persons")
    public Person updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    // Formatação desejada da idade
    @GetMapping("/ageCountData")
    public AgeCountData getAgeCountData() {
        return personService.getAgeCountData();
    }

    // Formatação desejada de relação de gênero
    @GetMapping("/genderCountByStates")
    public List<GenderByStateSortDto> findGenderCountByStates() {
        return personService.findGenderCountByStates();
    }

    @GetMapping("/stateGenderCountMinMaxData")
    public Map<String, List<MinMaxStateGenderCountDto>> getStateGenderCountMinMaxData() {
        return personService.getStateGenderCountMinMaxData();
    }
}

