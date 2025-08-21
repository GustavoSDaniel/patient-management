package com.gustavosdaniel.authservice.authentication;

import com.gustavosdaniel.authservice.commun.JwtUtil;
import com.gustavosdaniel.authservice.user.LoginRequestDTO;
import com.gustavosdaniel.authservice.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<String> token = userService
                .findByEmail(loginRequestDTO.getEmail())
                .filter(user -> passwordEncoder.matches(loginRequestDTO.getPassword(),
                        user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail(), user.getRole()));

        return token;
    }
}
