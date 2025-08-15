package org.gustavosdaniel.patientservice.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
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

        if (patientRepository.existsByEmail(requestPatientDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        Patient newPatient = patientRepository.save(patientMapper.toPatient(requestPatientDTO));


        return patientMapper.toPatientResponseDTO(newPatient);
    }

    @Override
    public Patient updatePatient(UUID id, RequestPatientDTO requestPatientDTO) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException());


    }
}
