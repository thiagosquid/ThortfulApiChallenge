package com.thiago.thortfulapichallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "User Randomize",
                description = "Api to generate User random info using the 3rd part API https://randomuser.me/")
)
public class ThortfulApiChallengeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThortfulApiChallengeApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
