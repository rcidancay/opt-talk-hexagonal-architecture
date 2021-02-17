package com.optare.vaccine.ui.controller.person.response;

import lombok.Data;

@Data
public class PersonResponse {
    private final String id;
    private final String name;
    private final Integer age;
    private final String job;
}