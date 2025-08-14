package org.gustavosdaniel.patientservice.patient;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public static interface PatientService {

        List<PatientResponseDTO> getPatients();

        PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO);
    }

    @Service
    @RequiredArgsConstructor
    public static class PatientServiceImpl implements PatientService {

        private final PatientController.PatientRepository patientRepository;
        private final PatientMapper patientMapper;


        @Override
        public List<PatientResponseDTO> getPatients() {

            List<Patient> patients = patientRepository.findAll();

            return patients.stream()
                    .map(patientMapper::toPatientResponseDTO).collect(Collectors.toList());

        }

        @Override
        public PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO) {
            Patient patient = patientRepository.save(patientMapper.toPatient(requestPatientDTO));

            return patientMapper.toPatientResponseDTO(patient);
        }
    }
}
