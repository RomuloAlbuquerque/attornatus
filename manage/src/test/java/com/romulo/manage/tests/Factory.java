package com.romulo.manage.tests;

import com.romulo.manage.dto.PersonDTO;
import com.romulo.manage.entities.Address;
import com.romulo.manage.entities.Person;

import java.time.LocalDate;

public class Factory {
	
	public static Person createPerson() {
		Person person = new Person(1L, "Roberto Carlos", LocalDate.parse("2007-12-03"));
		person.getAddresses().add(new Address(1L, "Rua das Mercês", "66030-158", 21, "Bujaru-PA", true));
		return person;		
	}
	
	public static PersonDTO createPersonDTO() {
		Person  person = createPerson();
		return new PersonDTO(person, person.getAddresses());
	}
}
