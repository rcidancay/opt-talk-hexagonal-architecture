package com.optare.vaccine.dto;

public class VaccineRequest {
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