package org.gustavosdaniel.patientservice.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gustavosdaniel.patientservice.address.AddressRequestDTO;

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

    @Valid
    private AddressRequestDTO addressRequestDTO;

    @NotNull(message = "O campo data de nascimento é obrigatório")
    @Past(message = "A data de nascimento deve ser no passado")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

}
