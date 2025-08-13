package org.gustavosdaniel.patientservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponseDTO {

    private UUID id;

    private String name;

    private String email;

    private String address;

    private LocalDate birthDate;

    private LocalDateTime registrationDate ;

}
