package org.gustavosdaniel.patientservice.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    private LocalDateTime registrationDate ;

}
