package org.gustavosdaniel.patientservice.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientMapper  patientMapper;

   private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody RequestPatientDTO requestPatientDTO
    ) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(requestPatientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);

    }

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {

        List<PatientResponseDTO> patients = patientService.getPatients();

        return ResponseEntity.ok(patients);

    }

}
