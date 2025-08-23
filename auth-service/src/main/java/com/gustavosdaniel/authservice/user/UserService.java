package com.gustavosdaniel.authservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface UserService {

    CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    Page<User> getUsers(Pageable pageable);

    Optional<User> findByEmail(String email);
}
