package com.thiago.thortfulapichallenge.service;

import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RandomUserService {

    private final RestTemplate restTemplate;
    private final String BASE_URL_API = "https://randomuser.me/api/?page=1&results=5000";

    public RandomUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<ResponseDTO<RandomUser>> getAllWithFilters() {
        ParameterizedTypeReference<ResponseDTO<RandomUser>> parameterizedTypeReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<ResponseDTO<RandomUser>> exchange = restTemplate.exchange(BASE_URL_API, HttpMethod.GET, null, parameterizedTypeReference);
        return exchange;
    }
}
