package com.optare.vaccine.application.vaccine.applier;

import com.optare.vaccine.application.vaccine.applier.exceptions.EveryBodyWasVaccinated;
import com.optare.vaccine.domain.person.Person;

import java.util.List;
import java.util.Optional;

import static com.optare.vaccine.domain.person.Job.HEALTH_PERSONNEL;
import static com.optare.vaccine.domain.person.Job.MAYOR;

public class PersonPool {
    private final List<Person> notVaccinatedPersons;

    protected PersonPool(List<Person> persons) {
        this.notVaccinatedPersons = persons;
    }

    protected Person pullByPriority() throws EveryBodyWasVaccinated {
        if (this.notVaccinatedPersons.isEmpty()) throw new EveryBodyWasVaccinated();

        Person person = getPersonByPriority();
        notVaccinatedPersons.remove(person);
        return person;
    }

    private Person getPersonByPriority() {

        Optional<Person> somebodyRetired = getSomebodyRetired();
        if (somebodyRetired.isPresent()) {
            System.out.println("Rule (Retired): " + somebodyRetired.get());
            return somebodyRetired.get();
        }

        Optional<Person> someBodyMayor = getSomebodyMayor();
        if (someBodyMayor.isPresent()) {
            System.out.println("Rule (Mayor): " + someBodyMayor.get());
            return someBodyMayor.get();
        }

        Optional<Person> somebodyHealthPersonnel = getSomebodyHealthPersonnel();
        if (somebodyHealthPersonnel.isPresent()) {
            System.out.println("Rule (Health Personnel): " + somebodyHealthPersonnel.get());
            return somebodyHealthPersonnel.get();
        }

        System.out.println("Rule (Other population): " + somebodyHealthPersonnel);

        return getFirst();
    }

    private Person getFirst() {
        return this.notVaccinatedPersons.get(0);
    }


    private Optional<Person> getSomebodyMayor() {
        return this.notVaccinatedPersons.stream().filter(person -> person.getJob().equals(MAYOR)).findFirst();
    }

    private Optional<Person> getSomebodyHealthPersonnel() {
        return this.notVaccinatedPersons.stream().filter(person -> person.getJob().equals(HEALTH_PERSONNEL)).findFirst();
    }

    private Optional<Person> getSomebodyRetired() {
        return this.notVaccinatedPersons.stream().filter(person -> person.getAge().isRetired()).findFirst();
    }

    protected boolean arePersonsNotVaccinated() {
        return !this.notVaccinatedPersons.isEmpty();
    }
}
