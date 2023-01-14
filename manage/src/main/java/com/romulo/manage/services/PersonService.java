package com.romulo.manage.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romulo.manage.dto.PersonDTO;
import com.romulo.manage.entities.Person;
import com.romulo.manage.repositories.PersonRepository;
import com.romulo.manage.services.exceptions.DatabaseException;
import com.romulo.manage.services.exceptions.ResourceNotFoundException;

@Service
public class PersonService {

	@Autowired
	private PersonRepository repository;

	@Transactional(readOnly = true)
	public Page<PersonDTO> findAllPaged(PageRequest pageRequest) {
		Page<Person> list = repository.findAll(pageRequest);
		return list.map(x -> new PersonDTO(x));
	}

	@Transactional(readOnly = true)
	public PersonDTO findById(Long id) {
		Optional<Person> obj = repository.findById(id);
		Person entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new PersonDTO(entity);
	}

	@Transactional
	public PersonDTO insert(PersonDTO dto) {
		Person entity = new Person(dto.getName(), dto.getBirthDate());
		entity = repository.save(entity);
		return new PersonDTO(entity);
	}

	@Transactional
	public PersonDTO update(Long id, PersonDTO dto) {
		try {
				Person entity = repository.getOne(id);
				entity.setName(dto.getName());
				entity.setBirthDate(dto.getBirthDate());
				entity = repository.save(entity);
				return new PersonDTO(entity);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found: "+id);
		}
	
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
		
		
	}
}