package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import com.feliperios.algamoneyapi.model.Entry;
import com.feliperios.algamoneyapi.repository.EntryRepository;
import com.feliperios.algamoneyapi.repository.filter.EntryFilter;
import com.feliperios.algamoneyapi.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
	@PreAuthorize("hasAuthority('ROLE_ENTRY_RETRIEVE')")
	public ResponseEntity<Page<Entry>> searchEntries(EntryFilter entryFilter, Pageable pageable) {
		return ResponseEntity.ok().body(repository.filter(entryFilter, pageable));
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ENTRY_RETRIEVE')")
	public ResponseEntity<Entry> getEntry(@PathVariable Long id) {
		Optional<Entry> queryResult = repository.findById(id);

		if (queryResult.isEmpty()) {
			String exceptionMessage = "No class " + this.getClass().getCanonicalName() + " entity with id " + id + " exists!";
			throw new EntityNotFoundException(exceptionMessage);
		}

		return ResponseEntity.ok().body(queryResult.get());
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_ENTRY_CREATE')")
	public ResponseEntity<Entry> createEntry(@Valid @RequestBody Entry newEntry, HttpServletResponse response) {
		Entry persistedEntry = service.save(newEntry);

		publisher.publishEvent(new ResourceCreationEvent(this, response, persistedEntry.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(persistedEntry);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_ENTRY_DELETE')")
	public void deleteEntry(@PathVariable Long id) {
		repository.deleteById(id);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_ENTRY_UPDATE')")
	public ResponseEntity<Entry> updateEntry(@PathVariable Long id, @RequestBody Entry updateData) {
		Entry updatedEntry = service.updateEntry(id, updateData);
		return ResponseEntity.ok().body(updatedEntry);
	}

}
