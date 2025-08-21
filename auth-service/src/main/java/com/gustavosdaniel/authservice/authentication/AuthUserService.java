package com.gustavosdaniel.authservice.authentication;

import com.gustavosdaniel.authservice.user.LoginRequestDTO;

import java.util.Optional;

public interface AuthUserService {

    Optional<String> authenticate(LoginRequestDTO loginRequestDTO);

    boolean validateToken(String substring);
}
