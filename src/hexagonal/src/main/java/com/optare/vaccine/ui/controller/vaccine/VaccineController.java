package com.optare.vaccine.ui.controller.vaccine;

import com.optare.vaccine.application.vaccine.applier.VaccineApplierUseCase;
import com.optare.vaccine.domain.vaccine.Vaccine;
import com.optare.vaccine.domain.vaccine.VaccineRepository;
import com.optare.vaccine.domain.vaccine.VaccineType;
import com.optare.vaccine.domain.vaccine.VaccineTypeNotAllowed;
import com.optare.vaccine.ui.controller.vaccine.request.VaccineRequest;
import com.optare.vaccine.ui.controller.vaccine.response.VaccineResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
public class VaccineController {

    private final VaccineApplierUseCase vaccineApplierUseCase;
    private final VaccineRepository vaccineRepository;

    private List<VaccineResponse> toVaccineResponses(List<Vaccine> vaccines) {
        return vaccines.stream()
                .map(v -> new VaccineResponse(v.getVaccineId().getValue(), v.getVaccineType().getValue()))
                .collect(Collectors.toList());
    }

    @GetMapping("/vaccines")
    public List<VaccineResponse> listOVaccined() {
        return toVaccineResponses(vaccineRepository.findAll());
    }


    @PutMapping("/vaccines")
    public ResponseEntity<?> vaccinePerson(@RequestBody VaccineRequest vaccineRequest) {
        try {
            VaccineType vaccineType = VaccineType.of(vaccineRequest.getVaccineType());
            vaccineApplierUseCase.apply(vaccineType);
            return new ResponseEntity<>("Vaccine applied: " + vaccineType.getValue(), HttpStatus.ACCEPTED);
        } catch (VaccineTypeNotAllowed vaccineTypeNotAllowed) {
            log.error(vaccineTypeNotAllowed.getMessage(), vaccineTypeNotAllowed);
            return new ResponseEntity<>(vaccineTypeNotAllowed.getMessage(), HttpStatus.CONFLICT);
        }

    }


}
