package com.romulo.manage.resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romulo.manage.entities.Person;

@RestController
@RequestMapping(value = "/categories")
public class PersonResource {
	
	@GetMapping
	public ResponseEntity<List<Person>> findAll() {
		List<Person> list = new ArrayList<>();
		list.add(new Person(1L, "Richard", LocalDate.parse("1987-03-20")));
		list.add(new Person(1L, "Rodrigo", LocalDate.parse("1988-12-21")));
		return ResponseEntity.ok().body(list);
	}
	
}
