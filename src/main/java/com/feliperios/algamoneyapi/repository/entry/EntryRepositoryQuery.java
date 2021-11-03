package com.feliperios.algamoneyapi.repository.entry;

import com.feliperios.algamoneyapi.model.Entry;
import com.feliperios.algamoneyapi.repository.filter.EntryFilter;
import com.feliperios.algamoneyapi.repository.projection.EntryDigest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryRepositoryQuery {

	public Page<Entry> filter(EntryFilter filter, Pageable pageable);

	public Page<EntryDigest> generateDigest(EntryFilter filter, Pageable pageable);
}
