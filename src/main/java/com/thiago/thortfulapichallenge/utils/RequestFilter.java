package com.thiago.thortfulapichallenge.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class RequestFilter {
    private String gender;
    private String password;
    private String nat;
    private String inc;
    private String exc;
    private Integer page;
    private Integer results;

}
