package org.gustavosdaniel.patientservice.mapper;


import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.dto.RequestPatientDTO;
import org.gustavosdaniel.patientservice.model.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PatientMapper {

    public  PatientResponseDTO toPatientResponseDTO(Patient patient) {

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

        public  Patient toPatient(RequestPatientDTO requestPatientDTO) {
            if (requestPatientDTO == null) {
                return null;
            }

            return Patient.builder()
                    .name(requestPatientDTO.getName())
                    .email(requestPatientDTO.getEmail())
                    .address(requestPatientDTO.getAddress())
                    .birthDate(requestPatientDTO.getBirthDate())
                    .registrationDate(LocalDateTime.now())
                    .build();
        }
}
