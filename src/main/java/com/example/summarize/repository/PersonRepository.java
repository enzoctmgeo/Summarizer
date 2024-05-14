package com.example.summarize.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.summarize.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}