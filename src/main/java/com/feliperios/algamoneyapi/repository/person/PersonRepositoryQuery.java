package com.feliperios.algamoneyapi.repository.person;

import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.filter.PersonFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonRepositoryQuery {
	public Page<Person> filter(PersonFilter filter, Pageable pageable);
}
