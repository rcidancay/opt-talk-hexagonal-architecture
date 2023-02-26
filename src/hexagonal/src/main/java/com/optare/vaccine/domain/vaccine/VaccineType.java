package com.optare.vaccine.domain.vaccine;

import lombok.Getter;

@Getter
public class VaccineType {

    public static final VaccineType COVID_19 = new VaccineType("covid-19");
    public static final VaccineType YELLOW_FEVER = new VaccineType("yellow-fever");
    private final String value;

    private VaccineType(String vaccineType) {
        this.value = vaccineType;
    }

    public static VaccineType of(String vaccineType) throws VaccineTypeNotAllowed {
        assert vaccineType != null;
        if(!COVID_19.getValue().equals(vaccineType) && !YELLOW_FEVER.getValue().equals(vaccineType)) throw new VaccineTypeNotAllowed(vaccineType);
        return new VaccineType(vaccineType);
    }
}
