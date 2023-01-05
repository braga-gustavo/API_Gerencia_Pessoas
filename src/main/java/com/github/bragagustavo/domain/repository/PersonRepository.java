package com.github.bragagustavo.domain.repository;

import com.github.bragagustavo.domain.entity.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional
    Optional<Person> findById(Long id);

}
