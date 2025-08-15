package org.gustavosdaniel.patientservice.address;

import jakarta.persistence.*;
import lombok.*;
import org.gustavosdaniel.patientservice.patient.Patient;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;
        return Objects.equals(id, address.id) && Objects.equals(number, address.number) && Objects.equals(street, address.street) && Objects.equals(zipcode, address.zipcode) && Objects.equals(complement, address.complement) && Objects.equals(neighborhood, address.neighborhood) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(createdAt, address.createdAt) && Objects.equals(updatedAt, address.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(number);
        result = 31 * result + Objects.hashCode(street);
        result = 31 * result + Objects.hashCode(zipcode);
        result = 31 * result + Objects.hashCode(complement);
        result = 31 * result + Objects.hashCode(neighborhood);
        result = 31 * result + Objects.hashCode(city);
        result = 31 * result + Objects.hashCode(state);
        result = 31 * result + Objects.hashCode(createdAt);
        result = 31 * result + Objects.hashCode(updatedAt);
        return result;
    }
}
