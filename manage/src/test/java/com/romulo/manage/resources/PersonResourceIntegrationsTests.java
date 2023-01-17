package com.romulo.manage.resources;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.romulo.manage.dto.PersonDTO;
import com.romulo.manage.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PersonResourceIntegrationsTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long countTotalPerson;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 1000L;
		countTotalPerson  = 4L;
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/person?page=0&size=12&sort=id,asc")
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.totalElements").value(countTotalPerson));
		result.andExpect(jsonPath("$.content").exists());		
		result.andExpect(jsonPath("$.content[0].name").value("Romulo Albuquerque"));
		result.andExpect(jsonPath("$.content[1].name").value("Daiane Soares"));
		result.andExpect(jsonPath("$.content[2].name").value("Ana Beatriz"));		
		result.andExpect(jsonPath("$.content[3].name").value("Murillo William"));
	}
	
	
	@Test
	public void updateShouldReturnPersonDTOWhenIdExists() throws Exception {
		
		PersonDTO personDTO = Factory.createPersonDTO();
		String jsonBody = objectMapper.writeValueAsString(personDTO);
		
		String expectedName = personDTO.getName();
		
		ResultActions result = 
				mockMvc.perform(put("/person/{id}", existingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.name").value(expectedName));
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		PersonDTO personDTO = Factory.createPersonDTO();
		String jsonBody = objectMapper.writeValueAsString(personDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/person/{id}", nonExistingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
}
