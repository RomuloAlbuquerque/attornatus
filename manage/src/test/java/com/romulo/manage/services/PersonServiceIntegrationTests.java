package com.romulo.manage.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.romulo.manage.dto.PersonDTO;
import com.romulo.manage.repositories.PersonRepository;
import com.romulo.manage.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class PersonServiceIntegrationTests {

	@Autowired
	private PersonService service;
	
	@Autowired
	private PersonRepository repository;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalPerson;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalPerson = 4L;
	}
	
	@Test
	public void deleteShouldDeleteResourceWhenIdExists() {
		
		service.delete(existingId);

		Assertions.assertEquals(countTotalPerson - 1, repository.count());
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}
	
	@Test
	public void findAllPagedShouldReturnPageWhenPage0Size10() {
		
		PageRequest pageRequest = PageRequest.of(0, 10);
		
		Page<PersonDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(0, result.getNumber());
		Assertions.assertEquals(10, result.getSize());
		Assertions.assertEquals(countTotalPerson, result.getTotalElements());
	}
	
	@Test
	public void findAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {
		
		PageRequest pageRequest = PageRequest.of(50, 10);
		
		Page<PersonDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertTrue(result.isEmpty());
	}
	
	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByName() {
		
		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("id"));
		
		Page<PersonDTO> result = service.findAllPaged(pageRequest);
		
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals("Romulo Albuquerque", result.getContent().get(0).getName());
		Assertions.assertEquals("Daiane Soares", result.getContent().get(1).getName());
		Assertions.assertEquals("Ana Beatriz", result.getContent().get(2).getName());
		Assertions.assertEquals("Murillo William", result.getContent().get(3).getName());
	}
}
