package org.gustavosdaniel.patientservice.patient;


import org.gustavosdaniel.patientservice.address.AddressMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class PatientMapper {

    private final AddressMapper addressMapper;

    public PatientMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public  PatientResponseDTO toPatientResponseDTO(Patient patient) {

        if (patient == null) {
            return null;
        }

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .addressResponseDTO(addressMapper.toAddressResponseDTO(patient.getAddress()))
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
                .address(addressMapper.toAddress(requestPatientDTO.getAddressRequestDTO()))
                .birthDate(requestPatientDTO.getBirthDate())
                .registrationDate(LocalDateTime.now())
                .build();
        }




}
