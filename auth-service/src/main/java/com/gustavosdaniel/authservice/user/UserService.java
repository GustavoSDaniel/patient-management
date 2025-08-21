package com.gustavosdaniel.authservice.user;

import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {

    Optional<User> findByEmail(String email);
}
