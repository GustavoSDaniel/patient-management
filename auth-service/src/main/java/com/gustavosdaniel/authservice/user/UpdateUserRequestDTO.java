package com.gustavosdaniel.authservice.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDTO {

    private String username;

    @Email(message = "Formato de email invalido")
    private String email;

    @Size(min = 8, message = "Password precisa ter no minimo 8 caracteres")
    private String password;


}
