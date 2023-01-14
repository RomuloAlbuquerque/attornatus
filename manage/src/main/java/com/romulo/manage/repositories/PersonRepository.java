package com.romulo.manage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romulo.manage.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
