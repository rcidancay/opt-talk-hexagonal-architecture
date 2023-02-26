package com.optare.vaccine.infra.jpa;

import com.optare.vaccine.domain.person.*;

import java.util.Collections;
import java.util.List;


public class PersonRepositoryJpa implements PersonRepository {
    @Override
    public List<Person> findAll() {
        //TODO Implement database option
        Person person = new Person(new PersonId("01"), new PersonName("Rafa From Database"), new Age(18), Job.OTHERS);
        return Collections.singletonList(person);
    }
}
