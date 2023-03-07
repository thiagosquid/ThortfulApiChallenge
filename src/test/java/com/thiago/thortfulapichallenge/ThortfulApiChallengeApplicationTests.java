package com.thiago.thortfulapichallenge;

import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThortfulApiChallengeApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void getARandomUser(CapturedOutput output) {
        ResponseEntity<ResponseDTO> response = this.testRestTemplate
                .exchange("/random-users", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                });

        assertTrue(output.getOut().contains("Request to URL="));
        Assertions.assertEquals(1, response.getBody().getResults().size());
    }

    @Test
    @Order(2)
    void getAnErrorMessageAfterDoManyRequests(CapturedOutput output) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        for (int i = 0; i < 6; i++) {
            response = this.testRestTemplate
                    .exchange("/random-users?results=5000", HttpMethod.GET, null, String.class);
        }
        assertTrue(output.getOut().contains("Request to URL="));
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode(), "Status Code different from 423");
        assertTrue(Objects.requireNonNull(response.getBody()).contains("{\"error\":\"Whoa, ease up there cowboy. You've requested"));
    }

}
