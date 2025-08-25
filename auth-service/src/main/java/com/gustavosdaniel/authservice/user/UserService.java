package com.gustavosdaniel.authservice.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface UserService {

    CreateUserResponseDTO createUser(CreateUserRequestDTO createUserRequestDTO);

    Page<User> getUsers(Pageable pageable);

    Page<CreateUserResponseDTO> searchUsersByName(Pageable pageable, String username);

    Optional<User> findByEmail(String email);

    UpdateUserResponseDTO updateUser(UUID id, UpdateUserRequestDTO updateUserRequestDTO);

    void deleteUser(UUID id);
}
