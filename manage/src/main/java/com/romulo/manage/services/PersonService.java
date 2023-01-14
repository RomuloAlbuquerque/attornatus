package com.romulo.manage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.romulo.manage.entities.Person;
import com.romulo.manage.repositories.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository repository;
	
	@Transactional(readOnly = true)
	public List<Person> findAll(){
		return repository.findAll();
	}
}