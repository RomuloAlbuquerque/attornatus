package com.romulo.manage.dto;

import java.io.Serializable;

import com.romulo.manage.entities.Address;

public class AddressDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	private String streetAddress;
	private String cep;
	private Integer number;
	private String city;
	private boolean main;
	
	public AddressDTO() {

	}
	
	public AddressDTO(Long id, String streetAddress, String cep, Integer number, String city, boolean main) {
		this.id = id;
		this.streetAddress = streetAddress;
		this.cep = cep;
		this.number = number;
		this.city = city;
		this.main = main;
	}
	
	public AddressDTO(Address entity) {
		this.id = entity.getId();
		this.streetAddress = entity.getStreetAddress();
		this.cep = entity.getCep();
		this.number = entity.getNumber();
		this.city = entity.getCity();
		this.main = entity.isMain();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public boolean isMain() {
		return main;
	}

	public void setMain(boolean main) {
		this.main = main;
	}

	@Override
	public String toString() {
		return "AddressDTO [id=" + id + ", streetAddress=" + streetAddress + ", cep=" + cep + ", number=" + number
				+ ", city=" + city + ", main=" + main + "]";
	}
	
	
	
}
