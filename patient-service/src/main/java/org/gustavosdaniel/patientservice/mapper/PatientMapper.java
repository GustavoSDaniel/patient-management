package org.gustavosdaniel.patientservice.mapper;


import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.model.Patient;


public class PatientMapper {

    public static PatientResponseDTO toPatientResponseDTO(Patient patient) {

        if (patient == null) {
            return null;
        }

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .birthDate(patient.getBirthDate())
                .registrationDate (patient.getRegistrationDate())
                .build();
    }
}
