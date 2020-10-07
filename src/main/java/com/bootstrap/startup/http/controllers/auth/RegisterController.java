package com.bootstrap.startup.http.controllers.auth;

import com.bootstrap.startup.http.requests.UserSignupRequest;
import com.bootstrap.startup.http.response.BasicResponse;
import com.bootstrap.startup.http.response.UserResponse;
import com.bootstrap.startup.models.User;
import com.bootstrap.startup.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;

    public RegisterController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 通过 @Valid 告知该 Request 对象需要校验
     * 通过 @RequestBody 把请求的 Body 赋值给该 Request 对象
     *
     * @return
     */
    @PostMapping
    public BasicResponse<UserResponse> register(@Valid @RequestBody UserSignupRequest request) {
        User user = authService.signup(request.toModel(User.class));

        // User Response 用于隐藏如密码一类
        return BasicResponse.ok(user.convertTo(UserResponse.class));
    }
}
