package com.bootstrap.startup.http.response;

import lombok.Data;

@Data
public class JwtResponse {
    private String jwt;

    public JwtResponse(String jwt) {
        this.jwt = jwt;
    }
}
