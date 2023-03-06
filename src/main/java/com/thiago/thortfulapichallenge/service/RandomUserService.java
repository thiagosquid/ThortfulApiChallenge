package com.thiago.thortfulapichallenge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RandomUserService {

    private final RestTemplate restTemplate;
    public final String BASE_URL_API = "https://randomuser.me/api/";

    public RandomUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseDTO getAllWithFilters(RequestFilter filter, @NonNull HttpServletResponse response) throws HttpClientErrorException, IOException {
        ParameterizedTypeReference<ResponseDTO> parameterizedTypeReference = new ParameterizedTypeReference<>() {
        };
        log.info("Executing request with filter={}", filter);

        String urlRequest = buildUrlRequest(filter);
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
