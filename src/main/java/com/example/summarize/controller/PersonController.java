package com.example.summarize.controller;

import com.example.summarize.model.Person;
import com.example.summarize.repository.PersonRepository;
import com.example.summarize.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonRepository repository;

    @Autowired
    private PersonService personService;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    public List<Person> getAllPersons() {
        return personService.findAll();
    }
    @PostMapping("/persons")
    public Person savePerson(@RequestBody Person person) {
        return personService.save(person);
    }
}
