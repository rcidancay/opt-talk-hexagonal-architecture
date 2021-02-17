package com.optare.vaccine.ui.controller.person;

import com.optare.vaccine.domain.person.Person;
import com.optare.vaccine.domain.person.PersonRepository;
import com.optare.vaccine.ui.controller.person.response.PersonResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    private List<PersonResponse> toPersonResponses(List<Person> persons) {
        return persons.stream()
                .map(p -> new PersonResponse(
                        p.getPersonId().getValue(),
                        p.getName().getValue(),
                        p.getAge().getValue(),
                        p.getJob().name()))
                .collect(Collectors.toList());
    }

    @GetMapping("/persons")
    public List<PersonResponse> listOfPersonsVaccined() {
            return toPersonResponses(personRepository.findAll());

    }
}
