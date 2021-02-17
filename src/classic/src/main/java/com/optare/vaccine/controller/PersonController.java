package com.optare.vaccine.controller;

import com.optare.vaccine.dto.PersonResponse;
import com.optare.vaccine.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public List<PersonResponse> listOfPersonsVaccined(@RequestParam(required = false) String vaccineType) {
        if (vaccineType == null) {
            return personService.findAllPersons();
        } else {
            return personService.findAllPersonsVaccinated(vaccineType);
        }
    }
}
