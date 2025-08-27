package com.gustavosdaniel.stack.authservice.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Formato de email invalido")
    private String email;

    @NotBlank(message = "Campo senha é obrigatório")
    @Size(min = 8, message = "Password precisa ter no minimo 8 caracteres")
    private String password;


}
