package com.thiago.thortfulapichallenge.utils;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestFilter {
    private String gender;
    private String password;
    private String nat;
    private String inc;
    private String exc;
    private Integer page;
    private Integer results;

}
