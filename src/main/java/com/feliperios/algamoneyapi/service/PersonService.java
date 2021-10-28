package com.feliperios.algamoneyapi.service;

import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PersonService {
	@Autowired
	private PersonRepository repository;

	public Person updatePerson(Long id, Person updateData) {
		Optional<Person> queryResult = repository.findById(id);

		if (queryResult.isEmpty()) {
			String exceptionMessage = "No class " + Person.class.getCanonicalName() + " entity with id " + id + " exists!";
			throw new EntityNotFoundException(exceptionMessage);
		}

		Person persistedPerson = queryResult.get();
		BeanUtils.copyProperties(updateData, persistedPerson, "id");
		return repository.save(persistedPerson);
	}
}
