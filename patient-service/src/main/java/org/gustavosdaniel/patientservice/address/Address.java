package org.gustavosdaniel.patientservice.address;

import jakarta.persistence.*;
import lombok.*;
import org.gustavosdaniel.patientservice.patient.Patient;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String zipcode;

    @Column(nullable = false)
    private String complement;

    @Column(nullable = false)
    private String neighborhood;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @OneToOne()
    private Patient patient;


}
