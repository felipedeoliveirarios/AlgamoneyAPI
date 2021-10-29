package com.feliperios.algamoneyapi.repository.entry;

import com.feliperios.algamoneyapi.model.Entry;
import com.feliperios.algamoneyapi.model.Entry_;
import com.feliperios.algamoneyapi.repository.filter.EntryFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class EntryRepositoryImpl implements EntryRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Entry> filter(EntryFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Entry> criteriaQuery = criteriaBuilder.createQuery(Entry.class);
		Root<Entry> root = criteriaQuery.from(Entry.class);

		Predicate[] predicates = generateRestrictions(filter, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Entry> typedQuery = entityManager.createQuery(criteriaQuery);
		int totalResults = typedQuery.getResultList().size();
		applyPageRestrictions(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, totalResults);
	}

	private Predicate[] generateRestrictions(EntryFilter filter, CriteriaBuilder criteriaBuilder, Root<Entry> root) {
		List<Predicate> predicateList = new ArrayList<>();

		// Gera um predicado para filtrar por descrição, caso esteja definido no filtro
		if (filter.getDescription() != null) {

			Predicate newPredicate = criteriaBuilder.like(
					criteriaBuilder.lower(root.get(Entry_.DESCRIPTION)),
					"%" + filter.getDescription() + "%"
			);

			predicateList.add(newPredicate);
		}

		// Gera um predicado para filtrar por data de vencimento mínima, caso esteja definido no filtro
		if (filter.getDueDateStart() != null) {

			Predicate newPredicate = criteriaBuilder.greaterThanOrEqualTo(
					root.get(Entry_.DUE_DATE),
					filter.getDueDateStart()
			);

			predicateList.add(newPredicate);
		}

		// Gera um predicado para filtrar por data de vencimento máxima, caso esteja definido no filtro
		if (filter.getDueDateEnd() != null) {
			Predicate newPredicate = criteriaBuilder.lessThanOrEqualTo(
					root.get(Entry_.DUE_DATE),
					filter.getDueDateEnd()
			);

			predicateList.add(newPredicate);
		}

		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

	private void applyPageRestrictions(TypedQuery<Entry> typedQuery, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int pageFirstEntry = currentPage * pageSize;

		typedQuery.setFirstResult(pageFirstEntry);
		typedQuery.setMaxResults(pageSize);
	}
}
