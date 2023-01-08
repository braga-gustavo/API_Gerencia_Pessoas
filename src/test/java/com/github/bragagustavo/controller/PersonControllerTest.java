package com.github.bragagustavo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bragagustavo.domain.entity.Address;
import com.github.bragagustavo.domain.entity.Person;
import com.github.bragagustavo.service.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

    static String PERSON_API = "/person";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PersonService personService;

    private static Person personBuilderWithId() {
        return Person.builder().id(1L).name("name").birthDate("date").build();
    }

    @Test
    @DisplayName("Should create person successfully")
    public void insertPersonTest() throws Exception {
        Person personRequest = personBuilder();
        Person personSaved = personBuilderWithId();

        String json = new ObjectMapper().writeValueAsString(personRequest);

        BDDMockito.given(personService.insertPerson(Mockito.any(Person.class))).willReturn(personSaved);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PERSON_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).
                 content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(personRequest.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("birthDate").value(personRequest.getBirthDate()));
    }

    @Test
    @DisplayName("Should throw an exeception when trying to create a person with not enough data")
    public void createInvalidPersonTest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new Person());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PERSON_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Should find a person by ID")
    public void findPersonTest() throws Exception {
        Person person = personBuilderWithId();

        BDDMockito.given(personService.findPerson(Mockito.any(Long.class))).willReturn(person);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(PERSON_API.concat("/1"));

        mockMvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(person.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(person.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("birthDate").value(person.getBirthDate()));
    }

    @Test
    @DisplayName("Should update a person")
    public void updatePersonTest() throws Exception {
        Long id = 1L;

        Person personToUpdate = Person.builder().name("name").build();

        Person personUpdated = Person.builder().id(id).name("other name").build();

        Person personFound = Person.builder().id(id).name("other name").birthDate("date").build();

        String json = new ObjectMapper().writeValueAsString(personToUpdate);

        BDDMockito.given(personService.updatePerson(personFound, personToUpdate)).willReturn(personUpdated);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(PERSON_API + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    private Person personBuilder() {
        return Person.builder()
                .name("name")
                .birthDate("date")
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
