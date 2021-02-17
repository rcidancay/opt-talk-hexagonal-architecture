package com.optare.vaccine.domain.person;

import lombok.ToString;

@ToString
public enum Job {
    HEALTH_PERSONNEL, MAYOR, OTHERS;

    public static Job of(String value){
        try {
           return Job.valueOf(value);
        }catch (IllegalArgumentException e){
            return OTHERS;
        }
    }
}
