package com.romulo.manage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romulo.manage.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
