package org.gustavosdaniel.patientservice.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.dto.RequestPatientDTO;
import org.gustavosdaniel.patientservice.mapper.PatientMapper;
import org.gustavosdaniel.patientservice.model.Patient;
import org.gustavosdaniel.patientservice.repository.PatientRepository;
import org.gustavosdaniel.patientservice.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;


    @Override
    public List<PatientResponseDTO> getPatients() {

        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toPatientResponseDTO).collect(Collectors.toList());

    }

    @Override
    public PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO) {
        return null;
    }
}
