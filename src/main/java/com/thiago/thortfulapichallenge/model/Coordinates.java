package com.thiago.thortfulapichallenge.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coordinates {
    private String latitude;
    private String longitude;
}