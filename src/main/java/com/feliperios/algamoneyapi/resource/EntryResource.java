package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import com.feliperios.algamoneyapi.model.Entry;
import com.feliperios.algamoneyapi.repository.EntryRepository;
import com.feliperios.algamoneyapi.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/entries")
public class EntryResource {
	@Autowired
	EntryRepository repository;

	@Autowired
	EntryService service;

	@Autowired
	ApplicationEventPublisher publisher;

	@GetMapping
	public ResponseEntity<List<Entry>> listEntries() {
		return ResponseEntity.ok().body(repository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Entry> getEntry(@PathVariable Long id) {
		Optional<Entry> queryResult = repository.findById(id);

		if (queryResult.isEmpty()) {
			String exceptionMessage = "No class " + this.getClass().getCanonicalName() + " entity with id " + id + " exists!";
			throw new EntityNotFoundException(exceptionMessage);
		}

		return ResponseEntity.ok().body(queryResult.get());
	}

	@PostMapping
	public ResponseEntity<Entry> createEntry(@Valid @RequestBody Entry newEntry, HttpServletResponse response) {
		Entry persistedEntry = service.save(newEntry);

		publisher.publishEvent(new ResourceCreationEvent(this, response, persistedEntry.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(persistedEntry);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEntry(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody Entry updateData) {
		Entry updatedEntry = service.updateEntry(id, updateData);
		return ResponseEntity.ok().body(updatedEntry);
	}

}