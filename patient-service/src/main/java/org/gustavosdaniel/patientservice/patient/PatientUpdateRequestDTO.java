package org.gustavosdaniel.patientservice.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class PatientUpdateRequestDTO {

    @NotBlank(message = "É obrigatório preencher o campo nome")
    private String name;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "O campo email é obrigatório")
    private String email;

    @NotNull(message = "O campo de endereço é obrigatório")
    @Valid
    private AddressRequestDTO addressRequestDTO;

}
