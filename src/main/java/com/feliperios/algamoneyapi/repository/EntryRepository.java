package com.feliperios.algamoneyapi.repository;

import com.feliperios.algamoneyapi.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {
}
