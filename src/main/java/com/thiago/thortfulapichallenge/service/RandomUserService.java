package com.thiago.thortfulapichallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class RandomUserService {

    private final RestTemplate restTemplate;

    @Value("${base.url.api}")
    public String BASE_URL_API;

    public RandomUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseDTO getAllWithFilters(RequestFilter filter, @NonNull HttpServletResponse response) throws HttpClientErrorException, IOException {
        ParameterizedTypeReference<ResponseDTO> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        log.info("Executing request with filter={}", filter);

        String urlRequest = BASE_URL_API + filter.buildUrlRequest();
        try {
            log.info("Request to URL={}", urlRequest);

            return restTemplate.exchange(urlRequest, HttpMethod.GET, null, parameterizedTypeReference).getBody();
        } catch (HttpClientErrorException e) {
            response.setStatus(e.getStatusCode().value());
            ParameterizedTypeReference<Map<String, String>> ptr = new ParameterizedTypeReference<>() {
            };
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), e.getResponseBodyAs(ptr));
            throw new HttpClientErrorException(e.getStatusCode());
        }
    }
}
