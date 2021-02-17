package com.optare.vaccine.application.vaccine.applier;

import com.optare.vaccine.domain.person.Person;
import com.optare.vaccine.domain.person.PersonRepository;
import com.optare.vaccine.domain.vaccine.Vaccine;
import com.optare.vaccine.domain.vaccine.VaccineRepository;
import com.optare.vaccine.domain.vaccine.VaccineType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VaccineApplierUseCase {

    private final PersonRepository personRepository;
    private final VaccineRepository vaccineRepository;

    public void apply(VaccineType vaccineType) {

        List<Person> persons = personRepository.findAll();
        List<Vaccine> vaccines = vaccineRepository.findAllByVaccineType(vaccineType);

        PersonPool personPool = new PersonPool(persons);
        VaccinePool vaccinePool = new VaccinePool(vaccines);

        while (vaccinePool.areThereVaccines() && personPool.arePersonsNotVaccinated()) {
            Person person = personPool.pullByPriority();
            Vaccine vaccine = vaccinePool.pull();

            vaccine.setPersonId(person.getPersonId());
            vaccineRepository.update(vaccine);
        }
    }

}
