package org.gustavosdaniel.patientservice.patient;

import lombok.RequiredArgsConstructor;
import org.gustavosdaniel.patientservice.address.Address;
import org.gustavosdaniel.patientservice.address.AddressMapper;
import org.gustavosdaniel.patientservice.grpc.BillingServiceGrpcClient;
import org.gustavosdaniel.patientservice.kafka.KafkaProducer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final AddressMapper addressMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;


    @Override
    public Page<PatientResponseDTO> getPatients(Pageable pageable) {

        Page<Patient> patients = patientRepository.findAll(pageable);

        return patients
                .map(patientMapper::toPatientResponseDTO);

    }

    @Override
    public PatientResponseDTO createPatient(RequestPatientDTO requestPatientDTO) {

        if (patientRepository.existsByEmail(requestPatientDTO.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // estabelecer relacionamento
        Patient newPatient = patientMapper.toPatient(requestPatientDTO);
        Address newAddress = addressMapper.toAddress(requestPatientDTO.getAddressRequestDTO());

        newPatient.setAddress(newAddress);
        newAddress.setPatient(newPatient);

        Patient savedPatient = patientRepository.save(newPatient);

        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),
                newPatient.getName(),
                newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return patientMapper.toPatientResponseDTO(savedPatient);
    }

    @Override
    public PatientUpdateResponseDTO updatePatient(UUID id, PatientUpdateRequestDTO patientUpdateRequestDTO) {

        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);


        if (patientRepository.existsByEmailAndIdNot(patientUpdateRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException();
        }

        Address updateAddress = addressMapper.toAddress(patientUpdateRequestDTO.getAddressRequestDTO());

        patient.setName(patientUpdateRequestDTO.getName());
        patient.setEmail(patientUpdateRequestDTO.getEmail());
        patient.setAddress(updateAddress);

        Patient updatedPatient = patientRepository.save(patient);

        return patientMapper.patientUpdateResponseDTO(updatedPatient);

    }

    @Override
    public void deletePatient(UUID id) {

        Patient patient = patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);

        patientRepository.delete(patient);
    }
}
