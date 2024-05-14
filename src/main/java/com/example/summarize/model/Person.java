package com.example.summarize.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    private String birthday;
    private Double latitude;
    private Double longitude;

    public void setGender(String field) {
        gender = field;
    }

    public void setTitle(String field) {
        title = field;
    }

    public void setGivenName(String field) {
        givenName = field;
    }

    public void setMiddleInitial(String field) {
        middleInitial = field;
    }

    public void setSurname(String field) {
        surname = field;
    }

    public void setState(String field) {
        state = field;
    }

    public void setEmail(String field) {
        email = field;
    }

    public void setBirthday(String field) {
        birthday = field;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getGender() {
        return gender;
    }

    public String getTitle() {
        return title;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public String getSurname() {
        return surname;
    }

    public String getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
