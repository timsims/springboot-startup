package com.bootstrap.startup.service;

import com.bootstrap.startup.models.User;
import com.bootstrap.startup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * 注册新用户
     *
     * @param user
     * @return
     */
    public User signup(User user) {

        var exist = userRepository.findByEmail(user.getEmail());
        if (exist != null) {
            return exist;
        }
        // bcrypt 编码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }
}
