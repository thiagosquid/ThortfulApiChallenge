package com.thiago.thortfulapichallenge.controller;

import com.thiago.thortfulapichallenge.model.ResponseDTO;
import com.thiago.thortfulapichallenge.service.RandomUserService;
import com.thiago.thortfulapichallenge.utils.RequestFilter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/random-users")
public class RandomUserController {

    private final RandomUserService randomUserService;

    public RandomUserController(RandomUserService randomUserService) {
        this.randomUserService = randomUserService;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns an object entity that contains a list of users " +
                    "and a info object", content = {@Content(mediaType = "application/json", schema =
            @Schema(implementation = ResponseDTO.class))})
    })
    @GetMapping(produces ={"application/json"})
    public ResponseEntity<ResponseDTO> getAllWithFilters(
            @RequestParam(name = "gender", required = false) String gender,
            @RequestParam(name = "password", required = false) String password,
            @RequestParam(name = "nat", required = false) String nat,
            @RequestParam(name = "inc", required = false) String inc,
            @RequestParam(name = "exc", required = false) String exc,
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "results", required = false) Integer results, @NonNull HttpServletResponse response) throws IOException {

        RequestFilter filter = RequestFilter.builder().gender(gender).password(password)
                .nat(nat).inc(inc).exc(exc).page(page).results(results).build();
        ResponseDTO allWithFilters = randomUserService.getAllWithFilters(filter, response);
        return ResponseEntity.ok(allWithFilters);

    }
}
