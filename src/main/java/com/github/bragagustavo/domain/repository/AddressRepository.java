package com.github.bragagustavo.domain.repository;

import com.github.bragagustavo.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {

}
