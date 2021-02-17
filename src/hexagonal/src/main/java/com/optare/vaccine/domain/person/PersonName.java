package com.optare.vaccine.domain.person;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PersonName {
    private final String value;

    public PersonName(String value) {
        assert value != null;
        this.value = value;
    }
}
