package com.optare.vaccine.service;

import com.jayway.jsonpath.JsonPath;
import com.optare.vaccine.dto.PersonRequest;
import com.optare.vaccine.dto.PersonResponse;
import com.optare.vaccine.dto.VaccineRequest;
import com.optare.vaccine.dto.VaccineResponse;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class VaccineService {

    public static final String HEALTH_PERSONNEL = "HEALTH_PERSONNEL";
    public static final String MAYOR = "MAYOR";
    public static final int RETIRED_AGE = 65;
    public static final String HTTP_SERVER = "http://localhost:1080";

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PersonService personService;

    public List<VaccineResponse> findAllVaccines() {
        List<VaccineResponse> vaccineResponses = new ArrayList<>();

        String urlVaccines = HTTP_SERVER + "/vaccines";
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(urlVaccines, String.class);
        JSONArray listOfVaccines = JsonPath.read(responseVaccines.getBody(), "$");
        for (Object vaccineResponse : listOfVaccines) {
            String id = JsonPath.read(vaccineResponse, "$.id");
            String type = JsonPath.read(vaccineResponse, "$.type");
            vaccineResponses.add(new VaccineResponse(id, type));
        }

        return vaccineResponses;
    }

    public void vaccine(VaccineRequest vaccineRequest) {

        List<PersonResponse> persons = personService.findAllPersons();

        String url = HTTP_SERVER + "/vaccines?notVaccinated=true&vaccineType=" + vaccineRequest.getVaccineType();
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
        JSONArray listOfVaccines = JsonPath.read(responseVaccines.getBody(), "$");
        int totalVaccines = listOfVaccines.size();

        List<String> personsVaccinated = new ArrayList<>();
        while (totalVaccines > 0 && personsVaccinated.size() < persons.size()) {
            for (Object vaccineResponse : listOfVaccines) {
                String id = JsonPath.read(vaccineResponse, "$.id");
                String type = JsonPath.read(vaccineResponse, "$.type");
                String personId = JsonPath.read(vaccineResponse, "$.personId");

                if (isVaccinated(personId)) {
                    totalVaccines = applyVaccine(persons, totalVaccines, personsVaccinated, id, type);
                }
            }
        }
    }

    private int applyVaccine(List<PersonResponse> persons, int totalVaccines, List<String> personsVaccinated, String id, String type) {
        for (PersonResponse person : persons) {
            boolean isNotVaccinated = !personsVaccinated.contains(person.getId());
            if (isNotVaccinated) {
                if (isRetired(person)) {
                    return vaccinatePerson(totalVaccines, personsVaccinated, id, type, person, "Rule (age > 65): ");
                } else if (notHasRetiredPersons(persons) && person.getJob().equals(MAYOR)) {
                    return vaccinatePerson(totalVaccines, personsVaccinated, id, type, person, "Rule (Mayor): ");
                } else if (notHasRetiredPersons(persons) && notHasMayors(persons) && person.getJob().equals(HEALTH_PERSONNEL)) {
                    return vaccinatePerson(totalVaccines, personsVaccinated, id, type, person, "Rule (Health Personnel): ");
                } else if (notHasRetiredPersons(persons) && !hasHealthPersonnel(persons) && notHasMayors(persons)) {
                    return vaccinatePerson(totalVaccines, personsVaccinated, id, type, person, "Rule (The rest of population): ");
                }
            }
        }
        return totalVaccines;
    }

    private boolean isRetired(PersonResponse person) {
        return person.getAge() > RETIRED_AGE;
    }

    private boolean isVaccinated(String personId) {
        return StringUtils.isEmpty(personId);
    }

    private int vaccinatePerson(int totalVaccines, List<String> personsVaccinated, String id, String type, PersonResponse person, String message) {
        String urlVaccine = HTTP_SERVER + "/vaccines/" + id;
        PersonRequest personRequest = new PersonRequest(person.getId());
        restTemplate.put(urlVaccine, personRequest);
        personsVaccinated.add(person.getId());
        totalVaccines--;
        person.setVaccinated(true);
        System.out.println(message + person.getName() + " was vaccinated (" + type + ")");
        return totalVaccines;
    }

    private boolean notHasMayors(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && p.getJob().equals(MAYOR)) return false;
        }
        return true;
    }

    private boolean hasHealthPersonnel(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && p.getJob().equals(HEALTH_PERSONNEL)) return true;
        }
        return false;
    }

    private boolean notHasRetiredPersons(List<PersonResponse> persons) {
        for (PersonResponse p : persons) {
            if (!p.isVaccinated() && isRetired(p)) return false;
        }
        return true;
    }
}
