package com.gustavosdaniel.stack.authservice.authentication;

import com.gustavosdaniel.stack.authservice.user.LoginRequestDTO;

import java.util.Optional;

public interface AuthUserService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    boolean validateToken(String substring);
}
