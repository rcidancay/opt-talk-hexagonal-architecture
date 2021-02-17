package com.optare.vaccine.domain.vaccine;

import lombok.Getter;

@Getter
public class VaccineId {
    private final String value;

    public VaccineId(String value) {
        assert value != null;
        this.value = value;
    }
}
