package com.feliperios.algamoneyapi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "entry")
public class Entry {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 96)
	private String description;

	@NotNull
	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "payment_date")
	private LocalDate paymentDate;

	@NotNull
	private BigDecimal value;

	@Size(max = 128)
	private String note;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EntryType type;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public EntryType getType() {
		return type;
	}

	public void setType(EntryType type) {
		this.type = type;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Entry entry = (Entry) o;
		return id.equals(entry.id) && description.equals(entry.description) && dueDate.equals(entry.dueDate) && Objects.equals(paymentDate, entry.paymentDate) && value.equals(entry.value) && Objects.equals(note, entry.note) && type == entry.type && category.equals(entry.category) && person.equals(entry.person);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}

