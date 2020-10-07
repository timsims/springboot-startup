package com.bootstrap.startup.http.controllers.auth;

import com.bootstrap.startup.components.jwt.JwtEncoder;
import com.bootstrap.startup.http.requests.LoginRequest;
import com.bootstrap.startup.http.response.BasicResponse;
import com.bootstrap.startup.http.response.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping
    public BasicResponse<JwtResponse> login(@Valid @RequestBody LoginRequest request) {

        // Authentication Manager 会根据 SecurityConfiguration 中的配置，通过 UserDetailsService 查询数据库中是否存在用户
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtEncoder.generateToken(userDetails);

        return BasicResponse.ok(new JwtResponse(jwt));
    }

    @GetMapping(path = "/test")
    public String loginTest() {
        return "success";
    }
}
