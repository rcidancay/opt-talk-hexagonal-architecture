package com.optare.vaccine.domain.person;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Person {
    private final PersonId personId;
    private final PersonName name;
    private final Age age;
    private final Job job;

    public Person(PersonId personId, PersonName name, Age age, Job job) {
        this.personId = personId;
        this.name = name;
        this.age = age;
        this.job = job;
    }


}
