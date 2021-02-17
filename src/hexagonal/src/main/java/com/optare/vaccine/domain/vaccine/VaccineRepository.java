package com.optare.vaccine.domain.vaccine;

import java.util.List;

public interface VaccineRepository {
    List<Vaccine> findAll();

    List<Vaccine> findAllByVaccineType(VaccineType vaccineType);

    void update(Vaccine vaccine);
}
