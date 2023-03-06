package com.thiago.thortfulapichallenge.service;

import com.thiago.thortfulapichallenge.model.Id;
import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RandomUserServiceTest {

    @InjectMocks
    private RandomUserService randomUserService;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Testing response of RandomUser object")
    void givenNoFilters_whenGetAllWithFilters_thenReturnJustOneRandomUser() throws IOException {
        //Given
        RequestFilter filter = RequestFilter.builder().build();

        ResponseDTO randomUserResponseDTO = new ResponseDTO();
        RandomUser randomUserReceived = new RandomUser();
        randomUserReceived.setId(new Id());
        randomUserResponseDTO.setResults(Arrays.asList(randomUserReceived));
        randomUserResponseDTO.setInfo(new ResponseDTO.Info());

        ResponseEntity<ResponseDTO> responseEntity = new ResponseEntity<>(randomUserResponseDTO, HttpStatus.OK);

        //When
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<ResponseDTO>>any()))
                .thenReturn(responseEntity);

        ResponseDTO response = randomUserService.getAllWithFilters(filter, httpServletResponse);

        //Then
        assertEquals(1, response.getResults().size());
    }

    @Test
    @DisplayName("Testing HttpClientErrorException")
    void givenNoFilters_whenGetAllWithFilters_thenThrowHttpClientErrorException() throws IOException {
        //Given
        RequestFilter filter = RequestFilter.builder().build();
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletResponse.setOutputStreamAccessAllowed(true);
        mockHttpServletResponse.setWriterAccessAllowed(true);
        HttpClientErrorException httpClientErrorException = new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
        httpClientErrorException.setBodyConvertFunction(ResolvableType::toString);

        //When
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.<HttpEntity<?>>any(),
                ArgumentMatchers.<ParameterizedTypeReference<ResponseDTO>>any()))
                .thenThrow(httpClientErrorException);

        Exception exception = assertThrows(HttpClientErrorException.class, () -> randomUserService.getAllWithFilters(filter, mockHttpServletResponse));

        //Then
        assertEquals(HttpClientErrorException.class, exception.getClass());
        HttpClientErrorException ex = (HttpClientErrorException) exception;
        assertEquals(HttpStatus.TOO_MANY_REQUESTS, ex.getStatusCode());
    }

}