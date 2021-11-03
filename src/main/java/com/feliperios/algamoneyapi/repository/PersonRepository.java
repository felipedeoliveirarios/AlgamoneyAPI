package com.feliperios.algamoneyapi.repository;

import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.repository.person.PersonRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long>, PersonRepositoryQuery {
}
