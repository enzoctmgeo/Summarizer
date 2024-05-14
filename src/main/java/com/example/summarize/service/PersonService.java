package com.example.summarize.service;

import com.example.summarize.model.Person;
import com.example.summarize.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }
    public Person save(Person person) {
        return personRepository.save(person);
    }
}
