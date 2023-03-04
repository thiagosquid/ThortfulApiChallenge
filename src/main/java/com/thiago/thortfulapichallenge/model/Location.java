package com.thiago.thortfulapichallenge.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private Street street;
    private String city;
    private String state;
    private String country;
    private Long postcode;
    private Coordinates coordinates;
    private Timezone timezone;

}