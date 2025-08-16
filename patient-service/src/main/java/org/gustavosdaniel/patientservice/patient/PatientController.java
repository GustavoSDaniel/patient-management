package org.gustavosdaniel.patientservice.patient;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {


   private final PatientService patientService;

    @PostMapping
    @Operation(summary = "Create a new Patients")
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody RequestPatientDTO requestPatientDTO
    ) {
        PatientResponseDTO patientResponseDTO = patientService.createPatient(requestPatientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientResponseDTO);

    }

    @GetMapping
    @Operation(summary = "Get All Patients")
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {

        List<PatientResponseDTO> patients = patientService.getPatients();

        return ResponseEntity.ok(patients);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientUpdateResponseDTO> updatePatient(
            @PathVariable UUID id,
            @Valid @RequestBody PatientUpdateRequestDTO patientUpdateRequestDTO

    ) {
        PatientUpdateResponseDTO patientUpdateResponseDTO = patientService.updatePatient(id, patientUpdateRequestDTO);

        return ResponseEntity.ok(patientUpdateResponseDTO);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }

}
