package org.gustavosdaniel.patientservice.patient;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@Slf4j
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
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {

        Pageable pageable = PageRequest.of(page -1, size);

        Page<PatientResponseDTO> patients = patientService.getPatients(pageable);

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

    @GetMapping(path = "/search")
    @Operation(summary = "Busca Pacientes por nome ")
    public ResponseEntity<Page<PatientResponseDTO>> searchPatientsByName(

            @RequestParam String name,
            @PageableDefault(size =  20, sort = "registrationDate", direction = Sort.Direction.DESC)
            Pageable pageable
    ){

        log.info("Searching users by name: {} with pagination: {}", name, pageable);

        Page<PatientResponseDTO> patients = patientService.searchUsersByPatients(name, pageable);

        return ResponseEntity.ok(patients);


    }

}
