package com.optare.vaccine.domain.vaccine;

import com.optare.vaccine.domain.person.PersonId;
import lombok.Getter;

@Getter
public class Vaccine {
    private final VaccineId vaccineId;
    private final VaccineType vaccineType;
    private PersonId personId;

    public Vaccine(VaccineId vaccineId, VaccineType vaccineType) {
        this.vaccineId = vaccineId;
        this.vaccineType = vaccineType;
        this.personId = null;
    }

    public void setPersonId(PersonId personId) {
        assert personId != null;
        this.personId = personId;
    }
}
