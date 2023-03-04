package com.thiago.thortfulapichallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Service
public class RandomUserService {

    private final RestTemplate restTemplate;
    private final String BASE_URL_API = "https://randomuser.me/api/";

    public RandomUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<ResponseDTO<RandomUser>> getAllWithFilters(RequestFilter filter, @NonNull HttpServletResponse response) throws IOException {
        ParameterizedTypeReference<ResponseDTO<RandomUser>> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };

        String urlRequest = buildUrlRequest(filter);
        try {
            return restTemplate.exchange(urlRequest, HttpMethod.GET, null, parameterizedTypeReference);
        } catch (HttpClientErrorException e) {
            response.setStatus(e.getStatusCode().value());
            ParameterizedTypeReference<Map<String, String>> ptr = new ParameterizedTypeReference<>() {
            };
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), e.getResponseBodyAs(ptr));
            return null;
        }
    }

    private String buildUrlRequest(RequestFilter filter) {
        String url = BASE_URL_API + "?";

        url += filter.getGender() != null ? "gender=" + filter.getGender().concat("&") : "";
        url += filter.getPassword() != null ? "password=" + filter.getPassword().concat("&") : "";
        url += filter.getNat() != null ? "nat=" + filter.getNat().concat("&") : "";
        url += filter.getInc() != null ? "inc=" + filter.getInc().concat("&") : "";
        url += filter.getExc() != null ? "exc=" + filter.getExc().concat("&") : "";
        url += filter.getPage() != null ? "page=" + filter.getPage().toString().concat("&") : "";
        url += filter.getResults() != null ? "results=" + filter.getResults().toString().concat("&") : "";

        return url;
    }
}
