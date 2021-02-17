package com.optare.vaccine.infra.rest;

import com.jayway.jsonpath.JsonPath;
import com.optare.vaccine.domain.vaccine.*;
import com.optare.vaccine.infra.rest.request.PersonRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.optare.vaccine.infra.rest.config.RestConfig.HTTP_SERVER;

@Service
@AllArgsConstructor
@Slf4j
public class VaccineRepositoryRest implements VaccineRepository {

    private static final String VACCINES_URL = HTTP_SERVER + "/vaccines";
    private final RestTemplate restTemplate;

    private List<Vaccine> toVaccines(JSONArray listOfVaccines) {
        List<Vaccine> vaccines = new ArrayList<>();
        for (Object vaccineResponse : listOfVaccines) {
            try {
                String id = JsonPath.read(vaccineResponse, "$.id");
                String type = JsonPath.read(vaccineResponse, "$.type");
                vaccines.add(new Vaccine(new VaccineId(id), VaccineType.of(type)));
            } catch (VaccineTypeNotAllowed e) {
                log.warn(e.getMessage(), e);
            }

        }
        return vaccines;
    }

    @Override
    public List<Vaccine> findAll() {
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(VACCINES_URL, String.class);
        JSONArray listOfVaccines = JsonPath.read(responseVaccines.getBody(), "$");
        return toVaccines(listOfVaccines);
    }

    @Override
    public List<Vaccine> findAllByVaccineType(VaccineType vaccineType) {
        String url = VACCINES_URL + "?notVaccinated=true&vaccineType=" + vaccineType.getValue();
        ResponseEntity<String> responseVaccines = restTemplate.getForEntity(url, String.class);
        JSONArray listOfVaccines = JsonPath.read(responseVaccines.getBody(), "$");
        return toVaccines(listOfVaccines);
    }

    @Override
    public void update(Vaccine vaccine) {
        String url = VACCINES_URL + "/" + vaccine.getVaccineId().getValue();
        PersonRequest personRequest = new PersonRequest(vaccine.getPersonId().getValue());
        restTemplate.put(url, personRequest);
    }
}
