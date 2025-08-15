package org.gustavosdaniel.patientservice.patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientResponseDTO> getPatients();

    PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO);

    Patient updatePatient(UUID id, RequestPatientDTO requestPatientDTO);
}
