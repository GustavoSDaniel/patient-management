package org.gustavosdaniel.patientservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, name = "birth_date")
    private LocalDate birthDate;

    @CreationTimestamp
    @Column(name = "registration_date ", updatable = false)
    private LocalDateTime registrationDate ;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id) && Objects.equals(name, patient.name) && Objects.equals(email, patient.email) && Objects.equals(address, patient.address) && Objects.equals(birthDate, patient.birthDate) && Objects.equals(registrationDate, patient.registrationDate) && Objects.equals(updatedAt, patient.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(birthDate);
        result = 31 * result + Objects.hashCode(registrationDate);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }
}
