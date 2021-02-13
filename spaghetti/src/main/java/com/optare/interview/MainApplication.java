package com.optare.interview;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @GetMapping("/persons")
    public List<PersonResponse> listOfPersonsVaccined(@RequestParam(required = false) String vaccineType) {

        if (vaccineType == null) {
            List<PersonResponse> result = new ArrayList<>();

            String url = "http://localhost:1080/persons";

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> responseUsers = restTemplate.getForEntity(url, String.class);
            JSONArray var0 = JsonPath.read(responseUsers.getBody(), "$");
            for (Object x : var0) {
                String var1 = JsonPath.read(x, "$.id");
                String var2 = JsonPath.read(x, "$.name");
                String var3 = JsonPath.read(x, "$.age");
                result.add(new PersonResponse(var1, var2, Integer.parseInt(var3)));
            }

            return result;
        } else {

            RestTemplate restTemplate = new RestTemplate();

            List<PersonResponse> persons = new ArrayList<>();
            String url2 = "http://localhost:1080/persons";
            ResponseEntity<String> responseUsers = restTemplate.getForEntity(url2, String.class);
            JSONArray var4 = JsonPath.read(responseUsers.getBody(), "$");
            for (Object x : var4) {
                String var1 = JsonPath.read(x, "$.id");
                String var2 = JsonPath.read(x, "$.name");
                String var3 = JsonPath.read(x, "$.age");
                persons.add(new PersonResponse(var1, var2, Integer.parseInt(var3)));
            }

            List<PersonResponse> personsVaccinated = new ArrayList<>();
            String url = "http://localhost:1080/vaccines?vaccineType=" + vaccineType;
            ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
            JSONArray var0 = JsonPath.read(responseVaccines.getBody(), "$");
            for (Object x : var0) {
                String var2 = JsonPath.read(x, "$.type");
                String var3 = JsonPath.read(x, "$.personId");

                if (vaccineType.equals(var2)) {
                    for (PersonResponse person : persons) {
                        if (var3.equals(person.getId())) {
                            personsVaccinated.add(person);
                        }
                    }
                }

            }

            return personsVaccinated;
        }
    }

    @PutMapping("/vaccines")
    public void vaccinePerson(@RequestBody VaccineRequest vaccineRequest) {


        RestTemplate restTemplate = new RestTemplate();

        List<PersonResponse> persons = new ArrayList<>();
        String url2 = "http://localhost:1080/persons";
        ResponseEntity<String> responseUsers = restTemplate.getForEntity(url2, String.class);
        JSONArray var4 = JsonPath.read(responseUsers.getBody(), "$");
        for (Object x : var4) {
            String var1 = JsonPath.read(x, "$.id");
            String var2 = JsonPath.read(x, "$.name");
            String var3 = JsonPath.read(x, "$.age");
            persons.add(new PersonResponse(var1, var2, Integer.parseInt(var3)));
        }

        String url = "http://localhost:1080/vaccines?vaccineType=" + vaccineRequest.getVaccineType();
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
        JSONArray var0 = JsonPath.read(responseVaccines.getBody(), "$");
      List<String> personsVaccinated = new ArrayList<>();
        for (Object x : var0) {
            String var1 = JsonPath.read(x, "$.id");
            String var2 = JsonPath.read(x, "$.type");
            String var3 = JsonPath.read(x, "$.personId");

            // Business logic
            if (StringUtils.isEmpty(var3)) {
                for (PersonResponse person : persons) {
                    if (person.getAge() > 30 && !personsVaccinated.contains(person.getId())) {
                        String url3 = "http://localhost:1080/vaccines/"+var1;
                        PersonRequest personRequest = new PersonRequest(person.getId());
                        restTemplate.put(url3, personRequest);
                        personsVaccinated.add(person.id);
                        System.out.println(person.getName() + " was vaccinated (" + var2+ ")");
                    }
                }
            }

        }


    }

    public class PersonResponse {

        private String id;
        private String name;
        private Integer age;

        public PersonResponse(String id, String name, Integer age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }

    private class VaccineResponse {

        private String id;
        private String type;

        public VaccineResponse(String id, String type) {
            this.id = id;
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class VaccineRequest {
        private String vaccineType;

        public VaccineRequest() {
        }

        public VaccineRequest(String vaccineType) {
            this.vaccineType = vaccineType;
        }

        public String getVaccineType() {
            return vaccineType;
        }

        public void setVaccineType(String vaccineType) {
            this.vaccineType = vaccineType;
        }
    }

    public static class PersonRequest {
        private String personId;

        public PersonRequest() {
        }

        public PersonRequest(String personId) {
            this.personId = personId;
        }

        public String getPersonId() {
            return personId;
        }

        public void setPersonId(String personId) {
            this.personId = personId;
        }
    }
}
