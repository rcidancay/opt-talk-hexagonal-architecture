package com.optare.vaccine.service;

import com.jayway.jsonpath.JsonPath;
import com.optare.vaccine.dto.PersonResponse;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    public static final String HTTP_SERVER = "http://localhost:1080";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<PersonResponse> findAllPersons() {
        List<PersonResponse> result = new ArrayList<>();

        String urlPersons = HTTP_SERVER + "/persons";

        ResponseEntity<String> responsePersons = restTemplate.getForEntity(urlPersons, String.class);
        JSONArray listOfPersons = JsonPath.read(responsePersons.getBody(), "$");
        for (Object personResponse : listOfPersons) {
            String id = JsonPath.read(personResponse, "$.id");
            String name = JsonPath.read(personResponse, "$.name");
            String age = JsonPath.read(personResponse, "$.age");
            String job = JsonPath.read(personResponse, "$.job");
            result.add(new PersonResponse(id, name, Integer.parseInt(age), job));
        }

        return result;
    }

}
