package org.gustavosdaniel.patientservice.patient;

import lombok.RequiredArgsConstructor;
import org.gustavosdaniel.patientservice.address.AddressMapper;
import org.gustavosdaniel.patientservice.commun.FusoHorarioBr;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AddressMapper addressMapper;


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
    public PatientUpdateResponseDTO updatePatient(UUID id, PatientUpdateRequestDTO patientUpdateRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);


        if (patientRepository.existsByEmailAndIdNot(patientUpdateRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException();
        }

        patient.setName(patientUpdateRequestDTO.getName());
        patient.setEmail(patientUpdateRequestDTO.getEmail());
        patient.setAddress(addressMapper.toAddress(patientUpdateRequestDTO.getAddressRequestDTO()));

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.patientUpdateResponseDTO(updatedPatient);

    }
}
