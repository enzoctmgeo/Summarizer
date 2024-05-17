package com.example.summarize.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gender;
    private String title;
    private String givenName;
    private String middleInitial;
    private String surname;
    private String state;
    private String email;

    @JsonIgnore
    private LocalDate birthday;

    private Double latitude;
    private Double longitude;

    public void setBirthday(String field) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        this.birthday = LocalDate.parse(field, formatter);
    }

    public int getAge() {
        return Period.between(this.birthday, LocalDate.now()).getYears();
    }
}
