package com.github.bragagustavo.service;

import com.github.bragagustavo.domain.entity.Address;
import com.github.bragagustavo.domain.entity.Person;
import com.github.bragagustavo.domain.repository.AddressRepository;
import com.github.bragagustavo.domain.repository.PersonRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    PersonService personService;

    @MockBean
    PersonRepository personRepository;

    @MockBean
    AddressRepository addressRepository;

    @BeforeEach
    public void setup() {
        this.personService = new PersonService(personRepository, addressRepository);
    }

    @Test
    @DisplayName("Should create a person")
    public void insertPersonTest() {

        // Cenario
        Long id = 1L;
        Person personRequest = personBuilder(null);
        Person personRespose = personBuilder(id);

        Mockito.when(personRepository.save(personRequest)).thenReturn(personRespose);

        //Execução
        Person personSaved = personService.insertPerson(personRequest);

        //Validação
        Assertions.assertThat(personSaved.getId()).isNotNull();
        Assertions.assertThat(personSaved.getId()).isEqualTo(1L);
        Assertions.assertThat(personSaved.getName()).isEqualTo(personRequest.getName());
        Assertions.assertThat(personSaved.getBirthDate()).isEqualTo(personRequest.getBirthDate());
        Assertions.assertThat(personSaved.getAddress().getId()).isEqualTo(1L);
        Assertions.assertThat(personSaved.getAddress().getMainAddress()).isEqualTo(personRequest.getAddress()
                .getMainAddress());
        Assertions.assertThat(personSaved.getAddress().getAddressNumber()).isEqualTo(personRequest.getAddress()
                .getAddressNumber());
        Assertions.assertThat(personSaved.getAddress().getCity()).isEqualTo(personRequest.getAddress().getCity());
        Assertions.assertThat(personSaved.getAddress().getStreet()).isEqualTo(personRequest.getAddress().getStreet());
        Assertions.assertThat(personSaved.getAddress().getCep()).isEqualTo(personRequest.getAddress().getCep());
    }

    @Test
    @DisplayName("Should get person by id")
    public void findPersonTest(){
        Long id = 1L;
        Person person = personBuilder(id);

        Mockito.when(personRepository.findById(id)).thenReturn(Optional.of(person));

        Person foundPerson = personService.findPerson(id);

        Assertions.assertThat(foundPerson.getId()).isEqualTo(id);
        Assertions.assertThat(foundPerson.getName()).isEqualTo(person.getName());
        Assertions.assertThat(foundPerson.getBirthDate()).isEqualTo(person.getBirthDate());
        Assertions.assertThat(foundPerson.getAddress()).isEqualTo(person.getAddress());

    }


    @Test
    @DisplayName("Should update a person")
    public void updatePersonTest(){
        Long id = 1L;
        Person personReceived = Person.builder().id(1L).build();
        Person personReturned = personBuilder(id);

        Mockito.when(personRepository.save(personReceived)).thenReturn(personReturned);

        Person personUpdate = personService.updatePerson(personReceived, personReturned);

        Assertions.assertThat(personUpdate.getId()).isEqualTo(id);
        Assertions.assertThat(personUpdate.getName()).isEqualTo(personReturned.getName());
        Assertions.assertThat(personUpdate.getBirthDate()).isEqualTo(personReturned.getBirthDate());
        Assertions.assertThat(personUpdate.getAddress()).isEqualTo(personReturned.getAddress());
    }

    @Test
    @DisplayName("Should delete a person")
    public void deletePersonTest(){
        Person person = personBuilder(1L);
        person.setAddress(null);

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(()-> personService.deletePerson(person));

        Mockito.verify(personRepository, Mockito.times(1)).delete(person);
    }

    @Test
    @DisplayName("Should not delete a person that does not exist")
    public void deleteInvalidPersonTest(){
        Person person = new Person();
        person.setAddress(null);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                ()-> personService.deletePerson(person)
        );

        Mockito.verify(personRepository, Mockito.never()).delete(person);
    }

    private Person personBuilder(Long id) {
        return Person.builder()
                .id(id)
                .name("name")
                .birthDate("date")
                .address(addressBuilder(id))
                .build();
    }

    private Address addressBuilder(Long id) {
        return Address.builder()
                .id(id)
                .street("street")
                .cep("cep")
                .city("city")
                .addressNumber("addressNumber")
                .mainAddress(false)
                .person(null)
                .build();
    }
}