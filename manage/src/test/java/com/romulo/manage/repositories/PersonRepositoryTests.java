package com.romulo.manage.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.romulo.manage.entities.Person;
import com.romulo.manage.tests.Factory;

@DataJpaTest
public class PersonRepositoryTests {

	@Autowired
	private PersonRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private long countTotalPerson;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalPerson = 4L;
	}
	
	@Test
	public void saveShouldPersistWithAutoincrementWhenIdIsNull() {

		Person person = Factory.createPerson();
		person.setId(null);
		
		person = repository.save(person);
		Optional<Person> result = repository.findById(person.getId());
		
		Assertions.assertNotNull(person.getId());
		Assertions.assertEquals(countTotalPerson + 1L, person.getId());
		Assertions.assertTrue(result.isPresent());
		Assertions.assertSame(result.get(), person);
	}
	
	@Test
	public void deleteShouldDeleteObjectWhenIdExists() {
		
		repository.deleteById(existingId);

		Optional<Person> result = repository.findById(existingId);
		
		Assertions.assertFalse(result.isPresent());
	}
	
	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(nonExistingId);			
		});
	}
}