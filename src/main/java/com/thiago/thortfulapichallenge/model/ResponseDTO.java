package com.thiago.thortfulapichallenge.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseDTO<T> {
    private List<T> results;
    private Info info;

    @Getter
    @Setter
    static class Info{
        private String seed;
        private Integer results;
        private Integer page;
        private String version;
    }
}

