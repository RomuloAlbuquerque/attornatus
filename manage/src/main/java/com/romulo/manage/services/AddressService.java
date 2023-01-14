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

import com.romulo.manage.dto.AddressDTO;
import com.romulo.manage.entities.Address;
import com.romulo.manage.repositories.AddressRepository;
import com.romulo.manage.services.exceptions.DatabaseException;
import com.romulo.manage.services.exceptions.ResourceNotFoundException;

@Service
public class AddressService {

	@Autowired
	private AddressRepository repository;

	@Transactional(readOnly = true)
	public Page<AddressDTO> findAllPaged(PageRequest pageRequest) {
		Page<Address> list = repository.findAll(pageRequest);
		return list.map(x -> new AddressDTO(x));
	}

	@Transactional(readOnly = true)
	public AddressDTO findById(Long id) {
		Optional<Address> obj = repository.findById(id);
		Address entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new AddressDTO(entity);
	}

	@Transactional
	public AddressDTO insert(AddressDTO dto) {
		Address entity = new Address();
		entity.setStreetAddress(dto.getStreetAddress());
		entity.setCep(dto.getCep());
		entity.setNumber(dto.getNumber());
		entity.setCity(dto.getCity());
		entity = repository.save(entity);
		return new AddressDTO(entity);
	}

	@Transactional
	public AddressDTO update(Long id, AddressDTO dto) {
		try {
				Address entity = repository.getOne(id);
				entity.setStreetAddress(dto.getStreetAddress());
				entity.setCep(dto.getCep());
				entity.setNumber(dto.getNumber());
				entity.setCity(dto.getCity());
				entity = repository.save(entity);
				return new AddressDTO(entity);
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