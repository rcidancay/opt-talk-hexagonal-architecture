package com.optare.vaccine.application.vaccine.applier.exceptions;

public class EveryBodyWasVaccinated extends RuntimeException {
    public EveryBodyWasVaccinated() {
        super("Everybody was vaccinated");
    }
}
