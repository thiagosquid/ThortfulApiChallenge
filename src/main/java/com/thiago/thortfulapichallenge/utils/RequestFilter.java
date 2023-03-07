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

    public String buildUrlRequest() {
        String query = "?";

        query += gender != null ? "gender=" + gender.concat("&") : "";
        query += password != null ? "password=" + password.concat("&") : "";
        query += nat != null ? "nat=" + nat.concat("&") : "";
        query += inc != null ? "inc=" + inc.concat("&") : "";
        query += exc != null ? "exc=" + exc.concat("&") : "";
        query += page != null ? "page=" + page.toString().concat("&") : "";
        query += results != null ? "results=" + results.toString().concat("&") : "";

        return query;
    }
}
