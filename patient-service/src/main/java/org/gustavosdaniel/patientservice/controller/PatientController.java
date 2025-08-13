package org.gustavosdaniel.patientservice.controller;

import lombok.RequiredArgsConstructor;
import org.gustavosdaniel.patientservice.dto.PatientResponseDTO;
import org.gustavosdaniel.patientservice.mapper.PatientMapper;
import org.gustavosdaniel.patientservice.model.Patient;
import org.gustavosdaniel.patientservice.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>> getAllPatients() {

        List<PatientResponseDTO> patients = patientService.getPatients();

        return ResponseEntity.ok(patients);

    }



}
