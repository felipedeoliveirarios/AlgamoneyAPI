package com.feliperios.algamoneyapi.resource;

import com.feliperios.algamoneyapi.event.ResourceCreationEvent;
import com.feliperios.algamoneyapi.model.Category;
import com.feliperios.algamoneyapi.repository.CategoryRepository;
import com.feliperios.algamoneyapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private CategoryService service;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public ResponseEntity<List<Category>> listCategories() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category newCategory, HttpServletResponse response) {
        Category persistedCategory = repository.save(newCategory);

        publisher.publishEvent(new ResourceCreationEvent(this, response, persistedCategory.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(persistedCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {
        Category foundCategory;

        try {
            foundCategory = repository.findById(id).get();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok().body(foundCategory);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category updateData) {
        System.out.println(updateData.getName());
        Category persistedCategory = service.updateCategory(id, updateData);
        return ResponseEntity.ok().body(persistedCategory);
    }
}
