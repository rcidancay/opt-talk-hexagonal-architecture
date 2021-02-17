package com.optare.vaccine.domain.person;

import java.util.List;

public interface PersonRepository {
    List<Person> findAll();
}
