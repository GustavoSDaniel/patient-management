package org.gustavosdaniel.patientservice.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PatientService {

    Page<PatientResponseDTO> getPatients(Pageable pageable);

    PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO);

    PatientUpdateResponseDTO updatePatient(UUID id, PatientUpdateRequestDTO patientUpdateRequestDTO);

    void deletePatient(UUID id);
}
