package com.thiago.thortfulapichallenge.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Picture {
    private String large;
    private String medium;
    private String thumbnail;
}