package com.bootstrap.startup.http.controllers.auth;

import com.bootstrap.startup.http.requests.LoginRequest;
import com.bootstrap.startup.models.User;
import com.bootstrap.startup.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private AuthService authService;

    @Autowired
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public User login(@Valid @RequestBody LoginRequest request) {

        var user = authService.attemptLogin(request.getEmail(), request.getPassword());
        return user;
    }
}
