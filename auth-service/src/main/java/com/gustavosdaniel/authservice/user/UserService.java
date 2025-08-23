package com.gustavosdaniel.authservice.user;

import java.util.Optional;


public interface UserService {

    CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    Optional<User> findByEmail(String email);
}
