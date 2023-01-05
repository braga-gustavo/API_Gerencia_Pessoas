package com.github.bragagustavo.domain.dto;

import com.github.bragagustavo.domain.entity.Address;
import lombok.AllArgsConstructor;

public class PersonDTO {

    private Address address;

    private String name;

    public PersonDTO(Address address, String name) {
        this.address = address;
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
