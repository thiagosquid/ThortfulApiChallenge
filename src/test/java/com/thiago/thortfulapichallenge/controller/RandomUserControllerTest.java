package com.thiago.thortfulapichallenge.controller;

import com.thiago.thortfulapichallenge.model.*;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    void givenANullRequestFilter_thenShouldFetchARandomUser() throws Exception {
        //Given
        RandomUser randomUserReceived = generateARandomUserObject();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .results(Arrays.asList(randomUserReceived)).info(getAnInfoObject()).build();

        given(randomUserService.getAllWithFilters(ArgumentMatchers.any(RequestFilter.class),
                ArgumentMatchers.any(HttpServletResponse.class))).willReturn(responseDTO);

        this.mockMvc.perform(get("/random-user").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].email").value(randomUserReceived.getEmail()))
                .andExpect(jsonPath("$.results[0].login.username").value(randomUserReceived.getLogin().getUsername()));
    }

    private RandomUser generateARandomUserObject() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return RandomUser.builder()
                .gender("female")
                .name(Name.builder().title("Ms").first("Brianna").last("Mason").build())
                .location(Location.builder().street(Street.builder().number(6912).name("Valley View Ln").build())
                        .city("Tacoma").state("Pennsylvania").country("United States").postcode("66448")
                        .coordinates(Coordinates.builder().latitude("84.4712").longitude("164.6115").build())
                        .timezone(Timezone.builder().offset("-3:30").description("Newfoundland").build())
                        .build())
                .email("brianna.mason@example.com")
                .login(Login.builder().uuid("28b4bdec-a75b-4409-8a52-2e4639e11c8e").username("bluedog918")
                        .password("duffy").salt("EvQrbJqQ").md5("6d368a86774fbf55709d950386d61147")
                        .sha1("4552e45e0b816adf47fe3d190b781c6ff51e1855")
                        .sha256("37a286eed6531ead225ec80113f7411fcd9e842a4b1adc043c1217903404ca98").build())
                .dob(Dob.builder().date(LocalDateTime.parse("1959-04-11T08:06:56.007Z", formatter)).age(63).build())
                .registered(Registered.builder().age(12).date(LocalDateTime.parse("2010-03-24T17:08:10.049Z", formatter)).build())
                .phone("(690) 275-9176")
                .cell("(852) 332-9646")
                .id(Id.builder().name("SSN").value("312-12-9610").build())
                .picture(Picture.builder().large("https://randomuser.me/api/portraits/women/19.jpg")
                        .medium("https://randomuser.me/api/portraits/med/women/19.jpg")
                        .thumbnail("https://randomuser.me/api/portraits/thumb/women/19.jpg").build())
                .nat("US")
                .build();
    }

    private ResponseDTO.Info getAnInfoObject() {
        return ResponseDTO.Info.builder().seed("b46c7b66672d5cbc").results(1).page(1).version("1.4").build();
    }
}