package com.feliperios.algamoneyapi.service;

import com.feliperios.algamoneyapi.exception.NullOrInactivePersonException;
import com.feliperios.algamoneyapi.model.Entry;
import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.EntryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class EntryService {
	@Autowired
	EntryRepository repository;

	@Autowired
	PersonService personService;

	public Entry save(Entry newEntry) {
		Person entryPerson = personService.findPersonById(newEntry.getPerson().getId());
		if (!entryPerson.getActive()) {
			String errorMessage = "A pessoa com id " + entryPerson.getId() + " não existe ou está inativa.";
			throw new NullOrInactivePersonException();
		}

		return repository.save(newEntry);
	}

	public Entry updateEntry(Long id, Entry updateData) {
		Optional<Entry> queryResult = repository.findById(id);

		if (queryResult.isEmpty()) {
			String exceptionMessage = "No class " + Entry.class.getCanonicalName() + " entity with id " + id + " exists!";
			throw new EntityNotFoundException(exceptionMessage);
		}

		Entry persistedEntry = queryResult.get();
		BeanUtils.copyProperties(updateData, persistedEntry, "id");
		return persistedEntry;
	}
}
