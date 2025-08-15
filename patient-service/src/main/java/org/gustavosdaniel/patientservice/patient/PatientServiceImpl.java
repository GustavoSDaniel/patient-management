package org.gustavosdaniel.patientservice.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
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
