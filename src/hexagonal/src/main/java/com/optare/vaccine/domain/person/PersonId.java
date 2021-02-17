package com.optare.vaccine.domain.person;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PersonId {
    private final String value;

    public PersonId(String value) {
        assert value!=null;
        this.value = value;
    }
}
