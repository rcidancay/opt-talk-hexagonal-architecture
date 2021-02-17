package com.optare.vaccine.application.vaccine.applier.exceptions;

public class ThereAreNoMoreVaccines extends RuntimeException {
    public ThereAreNoMoreVaccines() {
        super("There are not more vaccines");
    }
}
