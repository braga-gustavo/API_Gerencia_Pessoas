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

    public PersonService(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    public Person insertPerson(Person person) {
          Address address = person.getAddress();
          addressRepository.save(address);
          Person personSaved = personRepository.save(person);
          address.setPerson(personSaved);
          addressRepository.save(address);
          return personSaved;
    }

    public Person findPerson(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(() -> new ObjectNotFoundException("Pessoa não encontrada: " + id
                + "Nome: " + Person.class.getName()));
    }

    public List<Person> findAllPerson() {
        return personRepository.findAll();
    }

    public Person updatePerson(Person personToUpdate, Person person) {
        updatePersonData(personToUpdate, person);
        addressRepository.save(personToUpdate.getAddress());
        return personRepository.save(personToUpdate);
    }

    public void deletePerson(Person person) {
        if (person == null || person.getId() == null){
           throw new IllegalArgumentException("person can't be null");
        }
       try {
            personRepository.delete(person);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possivel exlcuir uma pessoa com dados cadastrados");
        }
    }

    private void updatePersonData(Person personToUpdate, Person person) {
        personToUpdate.setName(person.getName());
        personToUpdate.setAddress(person.getAddress());
    }

    public Person fromPersonDto(PersonDTO personDTO, Long id) {
        return new Person( id, personDTO.getName(), personDTO.getAddress());
    }

    public Boolean updateMainAddress(Person personToUpdate, Person person) {
        return personToUpdate.getAddress().getMainAddress();
    }
}
