package org.gustavosdaniel.patientservice.address;

import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressResponseDTO toAddressResponseDTO(Address address) {
        if (address == null) {
            return null;
        }

        return AddressResponseDTO.builder()
                .id(address.getId())
                .number(address.getNumber())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .build();
    }

    public Address toAddress(AddressRequestDTO addressRequestDTO) {

        if (addressRequestDTO == null) {
            return null;
        }

        return Address.builder()
                .number(addressRequestDTO.getNumber())
                .street(addressRequestDTO.getStreet())
                .zipcode(addressRequestDTO.getZipcode())
                .complement(addressRequestDTO.getComplement())
                .neighborhood(addressRequestDTO.getNeighborhood())
                .city(addressRequestDTO.getCity())
                .state(addressRequestDTO.getState())
                .build();
    }
}
