package org.gustavosdaniel.patientservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.dto.RequestPatientDTO;

import org.gustavosdaniel.patientservice.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

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
