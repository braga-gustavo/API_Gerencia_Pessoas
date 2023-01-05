package com.github.bragagustavo.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "pessoa")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Campo obrigatório")
    private String name;

    @Column(name = "birth")
    @NotEmpty(message = "Campo obrigatório")
    private String birthDate;
    @OneToOne
     private Address address;

    public Person(String name, String birthDate, Address address) {
        name = this.getName();
        birthDate = this.birthDate;
        address = this.getAddress();
    }

    public Person(Address address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


}
