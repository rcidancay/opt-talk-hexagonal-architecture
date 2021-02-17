package com.optare.vaccine.infra.rest;

import com.jayway.jsonpath.JsonPath;
import com.optare.vaccine.domain.person.*;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.optare.vaccine.infra.rest.config.RestConfig.HTTP_SERVER;

@Service
@AllArgsConstructor
public class PersonRepositoryRest implements PersonRepository {

    private static final String URL_PERSONS = HTTP_SERVER + "/persons";
    private final RestTemplate restTemplate;

    private List<Person> toPersons(JSONArray listOfPersons) {
        List<Person> persons = new ArrayList<>();
        for (Object personResponse : listOfPersons) {
            String id = JsonPath.read(personResponse, "$.id");
            String name = JsonPath.read(personResponse, "$.name");
            String age = JsonPath.read(personResponse, "$.age");
            String job = JsonPath.read(personResponse, "$.job");
            persons.add(new Person(new PersonId(id),
                    new PersonName(name),
                    new Age(Integer.parseInt(age)),
                    Job.of(job)));
        }
        return persons;
    }

    @Override
    public List<Person> findAll() {
        ResponseEntity<String> responsePersons = restTemplate.getForEntity(URL_PERSONS, String.class);
        JSONArray listOfPersons = JsonPath.read(responsePersons.getBody(), "$");
        return toPersons(listOfPersons);
    }

}
