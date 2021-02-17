package com.optare.vaccine.application.vaccine.applier;

import com.optare.vaccine.application.vaccine.applier.exceptions.ThereAreNoMoreVaccines;
import com.optare.vaccine.domain.vaccine.Vaccine;

import java.util.List;

public class VaccinePool {
    private final List<Vaccine> vaccines;

    public VaccinePool(List<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

    public boolean areThereVaccines() {
        return !vaccines.isEmpty();
    }

    protected Vaccine pull() {
        if(!areThereVaccines()) throw new ThereAreNoMoreVaccines();

        return this.vaccines.remove(0);
    }
}
