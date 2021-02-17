package com.optare.vaccine.domain.person;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Age {
    private final Integer value;

    public Age(Integer value) {
        assert value != null;
        this.value = value;
    }

    public boolean isRetired() {
        return this.value > 65;
    }
}
