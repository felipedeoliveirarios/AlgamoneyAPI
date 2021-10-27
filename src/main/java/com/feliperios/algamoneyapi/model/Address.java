package com.feliperios.algamoneyapi.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

@Embeddable
public class Address {
	@Column(name = "address_street")
	@Size(max = 96)
	private String street;

	@Column(name = "address_number")
	@Size(max = 8)
	private String number;

	@Column(name = "address_complement")
	@Size(max = 32)
	private String complement;

	@Column(name = "address_neighborhood")
	@Size(max = 32)
	private String neighborhood;

	@Column(name = "address_postal_code")
	@Size(max = 32)
	private String postalCode;

	@Column(name = "address_city")
	@Size(max = 32)
	private String city;

	@Column(name = "address_state")
	@Size(min=2, max=2)
	private String state;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
