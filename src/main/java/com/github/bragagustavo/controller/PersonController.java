package com.github.bragagustavo.controller;

import com.github.bragagustavo.domain.dto.PersonDTO;
import com.github.bragagustavo.domain.entity.Person;
import com.github.bragagustavo.service.AddressService;
import com.github.bragagustavo.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    AddressService addressService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insertPerson(@RequestBody Person person) {
        personService.insertPerson(person);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Person> findPerson(@PathVariable Long id) {
        Person person = personService.findPerson(id);
        return ResponseEntity.ok().body(person);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Person>> findAllPerson() {
        return ResponseEntity.ok().body(personService.findAllPerson());
    }

    @RequestMapping(value ="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO){
        Person person = personService.fromPersonDto(personDTO);
        person.setId(id);
        personService.updatePerson(person);
        return  ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        personService.deletePerson(id);
        return ResponseEntity.notFound().build();
    }
}
