package com.optare.vaccine.domain.vaccine;

public class VaccineTypeNotAllowed extends Exception {
    public VaccineTypeNotAllowed(String name) {
        super("Vaccine type not allowed " + name);
    }
}
