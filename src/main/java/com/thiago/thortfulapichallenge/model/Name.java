package com.thiago.thortfulapichallenge.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    private String title;
    private String first;
    private String last;
}