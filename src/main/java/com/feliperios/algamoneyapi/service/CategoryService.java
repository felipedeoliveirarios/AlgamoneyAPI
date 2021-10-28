package com.feliperios.algamoneyapi.service;

import com.feliperios.algamoneyapi.model.Category;
import com.feliperios.algamoneyapi.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository repository;

	public Category updateCategory(Long id, Category updateData) {
		Optional<Category> queryResult = repository.findById(id);

		if (queryResult.isEmpty()) {
			String exceptionMessage = "No class " + Category.class.getCanonicalName() + " entity with id " + id + " exists!";
			throw new EntityNotFoundException(exceptionMessage);
		}

		Category persistedCategory = queryResult.get();
		BeanUtils.copyProperties(updateData, persistedCategory, "id");
		return repository.save(persistedCategory);
	}
}
