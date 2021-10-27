package com.feliperios.algamoneyapi.repository;

import com.feliperios.algamoneyapi.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
