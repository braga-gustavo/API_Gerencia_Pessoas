package com.github.bragagustavo.service;

import com.github.bragagustavo.domain.dto.PersonDTO;
import com.github.bragagustavo.domain.entity.Address;
import com.github.bragagustavo.domain.entity.Person;
import com.github.bragagustavo.domain.repository.AddressRepository;
import com.github.bragagustavo.domain.repository.PersonRepository;
import com.github.bragagustavo.exceptions.DataIntegrityViolationException;
import com.github.bragagustavo.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    public void insertPerson(Person person) {
        Address address = person.getAddress();
        addressRepository.save(address);
        personRepository.save(person);
    }

    public Person findPerson(Long id ) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(()-> new ObjectNotFoundException("Pessoa não encontrada: " + id
                + "Nome: " + Person.class.getName()));
    }

    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    public Person updatePerson( Person  person) {
        Person personToUpdate = findPerson(person.getId());
        updatePersonData(personToUpdate, person);
        addressRepository.save(personToUpdate.getAddress());
        return personRepository.save(personToUpdate);
    }

    public void deletePerson(Long id) {
        findPerson(id);
       try {
            personRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
           throw new DataIntegrityViolationException("Não é possivel exlcuir uma pessoa com dados cadastrados");
       }
    }

    private void updatePersonData(Person personToUpdate, Person person) {
        personToUpdate.setName(person.getName());
        personToUpdate.setAddress(person.getAddress());
    }

    public Person fromPersonDto(PersonDTO personDTO) {
        return new Person(personDTO.getAddress(), personDTO.getName());
    }
}
