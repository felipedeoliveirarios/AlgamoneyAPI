package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import com.feliperios.algamoneyapi.model.Address;
import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.PersonRepository;
import com.feliperios.algamoneyapi.repository.filter.PersonFilter;
import com.feliperios.algamoneyapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/people")
public class PersonResource {

	@Autowired
	PersonRepository repository;

	@Autowired
	PersonService service;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PERSON_RETRIEVE')")
	ResponseEntity<Page<Person>> searchPeople(PersonFilter personFilter, Pageable pageable) {
		return ResponseEntity.ok().body(repository.filter(personFilter, pageable));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_PERSON_CREATE')")
	ResponseEntity<Person> createPerson(@Valid @RequestBody Person newPerson, HttpServletResponse response) {
		Person persistedPerson = repository.save(newPerson);

		publisher.publishEvent(new ResourceCreationEvent(this, response, persistedPerson.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(persistedPerson);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PERSON_RETRIEVE')")
	ResponseEntity<Person> getPerson(@PathVariable Long id) {
		Person foundPerson;

		try {
			foundPerson = repository.findById(id).get();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}

		return ResponseEntity.ok().body(foundPerson);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PERSON_DELETE')")
	public void deletePerson(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PERSON_UPDATE')")
	public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person updateData) {
		Person persistedPerson = service.updatePerson(id, updateData);
		return ResponseEntity.ok().body(persistedPerson);
	}

	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PERSON_UPDATE')")
	public void updatePersonActive(@PathVariable Long id, @RequestBody boolean newActiveValue) {
		Person persistedPerson = repository.getById(id);
		persistedPerson.setActive(newActiveValue);
		repository.save(persistedPerson);
	}

	@PutMapping("/{id}/name")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PERSON_UPDATE')")
	public void updatePersonName(@PathVariable Long id, @RequestBody String newName) {
		Person persistedPerson = repository.getById(id);
		persistedPerson.setName(newName);
		repository.save(persistedPerson);
	}

	@PutMapping("/{id}/address")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_PERSON_UPDATE')")
	public void updatePersonName(@PathVariable Long id, @RequestBody(required = false) Address newAddress) {
		Person persistedPerson = repository.getById(id);
		persistedPerson.setAddress(newAddress);
		repository.save(persistedPerson);
	}
}
