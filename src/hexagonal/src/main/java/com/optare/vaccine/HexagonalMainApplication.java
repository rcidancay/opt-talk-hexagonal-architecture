package com.optare.vaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.optare.vaccine.application, com.optare.vaccine.infra, com.optare.vaccine.domain" })
public class HexagonalMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalMainApplication.class, args);
    }

}
