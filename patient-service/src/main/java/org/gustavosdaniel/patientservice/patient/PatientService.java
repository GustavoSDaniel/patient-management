package org.gustavosdaniel.patientservice.patient;

import java.util.List;

public interface PatientService {

    List<PatientResponseDTO> getPatients();

    PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO);
}
