package org.gustavosdaniel.patientservice.patient;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {

    boolean existsByEmail (String email);

    // verificar se já existe um paciente com um determinado e-mail, excluindo o próprio paciente que está sendo atualizado
    @Query("SELECT COUNT(p) > 0 FROM Patient p WHERE p.email = :email AND p.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") UUID id);}
