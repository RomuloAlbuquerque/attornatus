package com.romulo.manage.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.romulo.manage.dto.PersonDTO;
import com.romulo.manage.services.PersonService;
import com.romulo.manage.services.exceptions.DatabaseException;
import com.romulo.manage.services.exceptions.ResourceNotFoundException;
import com.romulo.manage.tests.Factory;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PersonResource.class)
public class PersonResourceTests {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private PersonService service;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long nonExistingId;
	private Long dependentId;
	private PersonDTO personDTO;
	private PageImpl<PersonDTO> page;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		
		personDTO = Factory.createPersonDTO();
		page = new PageImpl<>(List.of(personDTO));
		
		when(service.findAllPaged(any())).thenReturn(page);

		when(service.findById(existingId)).thenReturn(personDTO);
		when(service.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);

		when(service.insert(any())).thenReturn(personDTO);
		
		when(service.update(eq(existingId), any())).thenReturn(personDTO);
		when(service.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);
		
		doNothing().when(service).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);
		doThrow(DatabaseException.class).when(service).delete(dependentId);
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(delete("/person/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(delete("/person/{id}", nonExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertShouldReturnPersonDTOCreated() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(personDTO);
		
		ResultActions result = 
				mockMvc.perform(post("/person")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void updateShouldReturnPersonDTOWhenIdExists() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(personDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/person/{id}", existingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(personDTO);
		
		ResultActions result = 
				mockMvc.perform(put("/person/{id}", nonExistingId)
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findAllShouldReturnPage() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/person")
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnPersonWhenIdExists() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/person/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.birthDate").exists());
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		ResultActions result = 
				mockMvc.perform(get("/person/{id}", nonExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	} 
}











