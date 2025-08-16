package org.gustavosdaniel.patientservice.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gustavosdaniel.patientservice.address.AddressResponseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientUpdateResponseDTO {

    private UUID id;

    private String name;

    private String email;

    private AddressResponseDTO addressResponseDTO;

    @JsonFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;



}
