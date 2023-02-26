package com.optare.vaccine.infra.jpa;

import com.optare.vaccine.domain.vaccine.Vaccine;
import com.optare.vaccine.domain.vaccine.VaccineId;
import com.optare.vaccine.domain.vaccine.VaccineRepository;
import com.optare.vaccine.domain.vaccine.VaccineType;

import java.util.Collections;
import java.util.List;

import static com.optare.vaccine.domain.vaccine.VaccineType.COVID_19;

public class VaccineRepositoryImpl implements VaccineRepository {
    @Override
    public List<Vaccine> findAll() {
        //TODO Implement database option
        Vaccine vaccine = new Vaccine(new VaccineId("01"),  COVID_19);
        return Collections.singletonList(vaccine);
    }

    @Override
    public List<Vaccine> findAllByVaccineType(VaccineType vaccineType) {
        //TODO Implement database option
        return Collections.singletonList(new Vaccine(new VaccineId("1"), COVID_19));
    }

    @Override
    public void update(Vaccine vaccine) {
        //TODO Implement database option
    }
}
