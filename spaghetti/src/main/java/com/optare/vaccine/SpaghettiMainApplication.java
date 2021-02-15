package com.optare.vaccine;

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
public class SpaghettiMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaghettiMainApplication.class, args);
    }

    @GetMapping("/vaccines")
    public List<VaccineResponse> listOVaccined() {

        List<VaccineResponse> vaccineResponses = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:1080/vaccines";
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
        JSONArray var0 = JsonPath.read(responseVaccines.getBody(), "$");
        for (Object x : var0) {
            String var1 = JsonPath.read(x, "$.id");
            String var2 = JsonPath.read(x, "$.type");
            vaccineResponses.add(new VaccineResponse(var1, var2));
        }

        return vaccineResponses;

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
                String var6 = JsonPath.read(x, "$.job");
                result.add(new PersonResponse(var1, var2, Integer.parseInt(var3), var6));
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
                String var6 = JsonPath.read(x, "$.job");
                persons.add(new PersonResponse(var1, var2, Integer.parseInt(var3), var6));
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
            String var6 = JsonPath.read(x, "$.job");
            persons.add(new PersonResponse(var1, var2, Integer.parseInt(var3), var6));
        }

        String url = "http://localhost:1080/vaccines?notVaccinated=true&vaccineType=" + vaccineRequest.getVaccineType();
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
        JSONArray var0 = JsonPath.read(responseVaccines.getBody(), "$");
        int totalVaccines = var0.size();

        List<String> personsVaccinated = new ArrayList<>();
        while (totalVaccines > 0 ) {
            for (Object x : var0) {
                String var1 = JsonPath.read(x, "$.id");
                String var2 = JsonPath.read(x, "$.type");
                String var3 = JsonPath.read(x, "$.personId");

                // Business logic
                if (StringUtils.isEmpty(var3)) {

                    for (PersonResponse person : persons) {
                        if (person.getAge() > 65 && !personsVaccinated.contains(person.getId())) {
                            String url3 = "http://localhost:1080/vaccines/" + var1;
                            PersonRequest personRequest = new PersonRequest(person.getId());
                            restTemplate.put(url3, personRequest);
                            personsVaccinated.add(person.id);
                            totalVaccines--;
                            person.setVaccinated(true);
                            System.out.println("Rule (age > 65): " + person.getName() + " was vaccinated (" + var2 + ")");
                        }
                        if (notHasRetiredPersons(persons) && person.getJob().equals("Mayor") && !personsVaccinated.contains(person.getId())) {
                            String url3 = "http://localhost:1080/vaccines/" + var1;
                            PersonRequest personRequest = new PersonRequest(person.getId());
                            restTemplate.put(url3, personRequest);
                            personsVaccinated.add(person.id);
                            totalVaccines--;
                            person.setVaccinated(true);
                            System.out.println("Rule (Mayor): " + person.getName() + " was vaccinated (" + var2 + ")");
                        }
                        if (notHasRetiredPersons(persons) && notHasMayors(persons) && person.getJob().equals("HealthPersonnel") && !personsVaccinated.contains(person.getId())) {
                            String url3 = "http://localhost:1080/vaccines/" + var1;
                            PersonRequest personRequest = new PersonRequest(person.getId());
                            restTemplate.put(url3, personRequest);
                            personsVaccinated.add(person.id);
                            totalVaccines--;
                            person.setVaccinated(true);
                            System.out.println("Rule (Health Personnel): " + person.getName() + " was vaccinated (" + var2 + ")");
                        }
                        if (notHasRetiredPersons(persons) && !hasHealthPersonnel(persons) && notHasMayors(persons) && !personsVaccinated.contains(person.getId())) {
                            String url3 = "http://localhost:1080/vaccines/" + var1;
                            PersonRequest personRequest = new PersonRequest(person.getId());
                            restTemplate.put(url3, personRequest);
                            personsVaccinated.add(person.id);
                            totalVaccines--;
                            person.setVaccinated(true);
                            System.out.println("Rule (The rest of population): " + person.getName() + " was vaccinated (" + var2 + ")");
                        }
                    }
                }
            }

        }


    }

    private boolean notHasMayors(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && p.getJob().equals("Mayor")) return false;
        }
        return true;
    }

    private boolean hasHealthPersonnel(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && p.getJob().equals("HealthPersonnel")) return true;
        }
        return false;
    }

    private boolean notHasRetiredPersons(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && p.getAge() > 65) return false;
        }
        return true;
    }

    public static class PersonResponse {

        private String id;
        private String name;
        private Integer age;
        private String job;
        private boolean vaccinated;

        public PersonResponse(String id, String name, Integer age, String job) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.job = job;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
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

        public void setVaccinated(boolean vaccinated) {
            this.vaccinated = vaccinated;
        }

        protected boolean isVaccinated() {
            return vaccinated;
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
