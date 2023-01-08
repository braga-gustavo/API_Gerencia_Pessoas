package com.github.bragagustavo.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Table
@NoArgsConstructor
@Builder
public class Address {

    @Id
    @Column
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Campo obrigatorio")
    private String street;

    @NotEmpty(message = "Campo obrigatorio")
    private String cep;

    @NotEmpty(message = "Campo obrigatorio")
    private String city;

    @NotEmpty
    private String addressNumber;

    private Boolean mainAddress = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Person person;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(Boolean mainAddress) {
        this.mainAddress = mainAddress;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
