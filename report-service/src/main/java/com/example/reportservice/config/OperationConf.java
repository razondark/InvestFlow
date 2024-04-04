package com.example.reportservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
@Configuration
public class OperationConf {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
