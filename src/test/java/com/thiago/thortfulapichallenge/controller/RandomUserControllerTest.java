package com.thiago.thortfulapichallenge.controller;

import com.thiago.thortfulapichallenge.model.Id;
import com.thiago.thortfulapichallenge.model.RandomUser;
import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.service.RandomUserService;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RandomUserController.class)
class RandomUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RandomUserService randomUserService;

    @Test
    void shouldFetchARandomUser() throws Exception {
        //Given
//        RequestFilter filter = RequestFilter.builder().gender("male").build();

        ResponseDTO<RandomUser> responseDTO = new ResponseDTO<>();
        RandomUser randomUserReceived = new RandomUser();
        randomUserReceived.setId(new Id());
        randomUserReceived.setEmail("weerwrwerw");
        responseDTO.setResults(Arrays.asList(randomUserReceived));
        responseDTO.setInfo(new ResponseDTO.Info());


        given(randomUserService.getAllWithFilters(ArgumentMatchers.any(RequestFilter.class),
                ArgumentMatchers.any(HttpServletResponse.class))).willReturn(responseDTO);

        this.mockMvc.perform(get("/random-user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].email").value("weerwrwerw"))
//                .andReturn()
        ;

    }
}