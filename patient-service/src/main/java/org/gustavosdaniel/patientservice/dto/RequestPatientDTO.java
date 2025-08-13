package org.gustavosdaniel.patientservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPatientDTO {

    @NotBlank(message = "É obrigatório preencher o campo nome")
    private String name;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "O campo email é obrigatório")
    private String email;

    @NotBlank(message = "O campo endereço é obrigatório ")
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,-]+$",
            message = "Formato de endereço inválido")
    private String address;

    @NotNull(message = "O campo data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate birthDate;

}
