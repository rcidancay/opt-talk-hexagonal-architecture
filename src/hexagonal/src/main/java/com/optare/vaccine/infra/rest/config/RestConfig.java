package com.optare.vaccine.infra.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    public static final String HTTP_SERVER = "http://localhost:1080";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
