package com.gustavosdaniel.stack.authservice.user;

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
public class CreateUserRequestDTO {

    @NotBlank(message = "Campo Usuario é obrigatório")
    private String username;

    @NotBlank(message = "Campo email é obrigatório")
    @Email(message = "Formato de email invalido")
    private String email;

    @NotBlank(message = "Campo senha é obrigatório")
    @Size(min = 8, message = "Password precisa ter no minimo 8 caracteres")
    private String password;


}
