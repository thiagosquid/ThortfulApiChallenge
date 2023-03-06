package com.thiago.thortfulapichallenge.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Registered {
    private LocalDateTime date;
    private Integer age;
}
