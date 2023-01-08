package com.github.bragagustavo.controller;

import com.github.bragagustavo.domain.dto.PersonDTO;
import com.github.bragagustavo.domain.entity.Person;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person insertPerson(@RequestBody @Valid Person person) {
        return personService.insertPerson(person);
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO) {
        Person person = personService.fromPersonDto(personDTO, id);
        Person personFound = personService.findPerson(id);
        personService.updatePerson(personFound, person);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/address/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateMainAddress(@PathVariable Long id, @Valid @RequestBody PersonDTO personDTO,
                                                     Person personToUpdate) {
        Person person = personService.fromPersonDto(personDTO, id);
        personService.updateMainAddress(person, personToUpdate);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Person person = personService.findPerson(id);
        personService.deletePerson(person);
        return ResponseEntity.notFound().build();
    }
}
