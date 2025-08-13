package org.gustavosdaniel.patientservice.service;

import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.dto.RequestPatientDTO;

import java.util.List;

public interface PatientService {

    List<PatientResponseDTO> getPatients();

    PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO);
}
