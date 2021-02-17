package com.optare.vaccine.controller;

import com.optare.vaccine.dto.VaccineRequest;
import com.optare.vaccine.dto.VaccineResponse;
import com.optare.vaccine.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/vaccines")
    public List<VaccineResponse> listOVaccined() {
        return vaccineService.findAllVaccines();
    }


    @PutMapping("/vaccines")
    public void vaccinePerson(@RequestBody VaccineRequest vaccineRequest) {
        vaccineService.vaccine(vaccineRequest);
    }


}
