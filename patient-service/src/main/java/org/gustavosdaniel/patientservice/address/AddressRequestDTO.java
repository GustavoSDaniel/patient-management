package org.gustavosdaniel.patientservice.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank(message = "O campo numero é obrigatório")
    @Pattern(regexp = "\\d+") // apenas numeros
    private String number;

    @NotBlank(message = "O campo rua é obrigatório")
    @Pattern(regexp = "^[\\p{L}0-9\\s.,-]+$",
            message = "O formato do nome da rua é inválido")
    private String street;

    @NotBlank(message = "O campo cep é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$",
            message = "Formato de CEP inválido (use XXXXX-XXX ou XXXXXXXX)")
    private String zipcode;

    private String complement;

    @NotBlank(message = "O campo bairro é obrigatório")
    private String neighborhood;

    @NotBlank(message = "O campo cidade é obrigatório")
    private String city;

    @NotBlank(message = "O campo estado é obrigatório")
    private String state;

}
