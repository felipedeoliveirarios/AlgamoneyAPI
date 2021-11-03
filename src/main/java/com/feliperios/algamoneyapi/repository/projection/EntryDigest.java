package com.feliperios.algamoneyapi.repository.projection;

import com.feliperios.algamoneyapi.model.EntryType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class EntryDigest {
	private Long id;
	private String description;
	private LocalDate dueDate, paymentDate;
	private BigDecimal value;
	private EntryType entryType;
	private String category;
	private String person;

	public EntryDigest(Long id, String description, LocalDate dueDate, LocalDate paymentDate, BigDecimal value, EntryType entryType, String category, String person) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.paymentDate = paymentDate;
		this.value = value;
		this.entryType = entryType;
		this.category = category;
		this.person = person;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public EntryType getEntryType() {
		return entryType;
	}

	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
}
