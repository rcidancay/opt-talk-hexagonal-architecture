package com.optare.vaccine.dto;

public class PersonRequest {
    private String personId;

    public PersonRequest() {
    }

    public PersonRequest(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}