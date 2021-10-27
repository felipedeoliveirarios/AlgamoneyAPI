package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonResource {

	@Autowired
	PersonRepository repository;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping
	ResponseEntity<List<Person>> ListPeople() {
		return ResponseEntity.ok().body(repository.findAll());
	}

	@PostMapping
	ResponseEntity<Person> CreatePerson(@Valid @RequestBody Person newPerson, HttpServletResponse response) {
		Person persistedPerson = repository.save(newPerson);

		publisher.publishEvent(new ResourceCreationEvent(this, response, persistedPerson.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(persistedPerson);
	}

	@GetMapping("/{id}")
	ResponseEntity<Person> GetPerson(@PathVariable Long id){
		Person foundPerson;

		try{
			foundPerson = repository.findById(id).get();
		} catch (Exception e){
			return ResponseEntity.badRequest().body(null);
		}

		return ResponseEntity.ok().body(foundPerson);
	}
}
