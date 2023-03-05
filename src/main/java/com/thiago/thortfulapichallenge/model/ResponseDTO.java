package com.thiago.thortfulapichallenge.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseDTO<T> {
    private List<T> results;
    private Info info;

    @Getter
    @Setter
    @ToString
    public static class Info{
        private String seed;
        private Integer results;
        private Integer page;
        private String version;
    }
}

