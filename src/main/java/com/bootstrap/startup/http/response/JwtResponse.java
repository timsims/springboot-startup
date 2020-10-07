package com.bootstrap.startup.http.response;

import com.bootstrap.startup.components.jwt.JwtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JwtResponse {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType = "bearer";

    @JsonProperty("expires_in")
    private Integer expiresIn = JwtUtil.getExpired();

    public JwtResponse(String jwt) {
        this.accessToken = jwt;
    }
}
