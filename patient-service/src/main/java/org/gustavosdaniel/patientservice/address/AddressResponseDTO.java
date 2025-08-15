package org.gustavosdaniel.patientservice.address;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gustavosdaniel.patientservice.patient.Patient;
import org.gustavosdaniel.patientservice.patient.PatientResponseDTO;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponseDTO {

    private UUID id;

    private String number;

    private String street;

    private String zipcode;

    private String complement;

    private String neighborhood;

    private String city;

    private String state;

}
