package com.feliperios.algamoneyapi.repository.person;

import com.feliperios.algamoneyapi.model.Person;
import com.feliperios.algamoneyapi.model.Person_;
import com.feliperios.algamoneyapi.repository.filter.PersonFilter;
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

public class PersonRepositoryImpl implements PersonRepositoryQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Person> filter(PersonFilter filter, Pageable pageable) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);
		Root<Person> root = criteriaQuery.from(Person.class);

		Predicate[] predicates = generateRestrictions(filter, criteriaBuilder, root);
		criteriaQuery.where(predicates);

		TypedQuery<Person> typedQuery = entityManager.createQuery(criteriaQuery);
		int totalResults = typedQuery.getResultList().size();
		applyPageRestrictions(typedQuery, pageable);

		return new PageImpl<>(typedQuery.getResultList(), pageable, totalResults);
	}

	private Predicate[] generateRestrictions(PersonFilter filter, CriteriaBuilder criteriaBuilder, Root<Person> root) {
		List<Predicate> predicateList = new ArrayList<>();

		//Gera o predicado para filtrar por nome
		if (filter.getName() != null) {
			Predicate newPredicate = criteriaBuilder.like(
					criteriaBuilder.lower(root.get(Person_.NAME)),
					"%" + filter.getName() + "%"
			);

			predicateList.add(newPredicate);
		}

		return predicateList.toArray(new Predicate[predicateList.size()]);
	}

	private void applyPageRestrictions(TypedQuery<Person> typedQuery, Pageable pageable) {
		int currentPage = pageable.getPageNumber();
		int pageSize = pageable.getPageSize();
		int pageFirstPerson = currentPage * pageSize;

		typedQuery.setFirstResult(pageFirstPerson);
		typedQuery.setMaxResults(pageSize);
	}

}
