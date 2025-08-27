package com.gustavosdaniel.stack.authservice.user;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public CreateUserResponseDTO toCreateUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return CreateUserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public User toUser(CreateUserRequestDTO createUserRequestDTO) {
        if (createUserRequestDTO == null) {
            return null;
        }

        return User.builder()
                .username(createUserRequestDTO.getUsername())
                .email(createUserRequestDTO.getEmail())
                .password(createUserRequestDTO.getPassword())
                .build();
    }

    public UpdateUserResponseDTO toUpdateUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        return UpdateUserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
