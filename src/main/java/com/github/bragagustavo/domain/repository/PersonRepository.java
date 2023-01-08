package com.github.bragagustavo.domain.repository;

import com.github.bragagustavo.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
