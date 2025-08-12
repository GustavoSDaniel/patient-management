package org.gustavosdaniel.patientservice.service;

import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;

import java.util.List;

public interface PatientService {

    List<PatientResponseDTO> getPatients();
}
