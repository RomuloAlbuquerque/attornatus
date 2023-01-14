package com.romulo.manage.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.romulo.manage.entities.Person;

public class PersonDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String name;
	private LocalDate birthDate;
	
	public PersonDTO() {
		
	}

	public PersonDTO(Long id, String name, LocalDate birthDate) {
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}
	
	public PersonDTO(Person entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.birthDate = entity.getBirthDate();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	
	
	
}