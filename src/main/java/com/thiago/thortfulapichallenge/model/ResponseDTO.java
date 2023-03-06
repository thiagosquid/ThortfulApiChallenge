package com.thiago.thortfulapichallenge.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private List<RandomUser> results;
    private Info info;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Info {
        private String seed;
        private Integer results;
        private Integer page;
        private String version;
    }
}

