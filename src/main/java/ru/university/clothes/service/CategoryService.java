package ru.university.clothes.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.CategoryRequest;
import ru.university.clothes.dto.CategoryResponse;
import ru.university.clothes.model.Category;
import ru.university.clothes.repository.CategoryRepository;

@Service 
@RequiredArgsConstructor 
public class CategoryService {

    private final CategoryRepository repository;

    @Cacheable (cacheNames = "categories", key = "'all'")
    public List<CategoryResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @CacheEvict (cacheNames = "categories", allEntries = true)
    public CategoryResponse create(CategoryRequest request) {
        Category category = Category.builder().name(request.name()).build();
        return toResponse(repository.save(category));
    }

    @CacheEvict (cacheNames = "categories", allEntries = true)
    public CategoryResponse update(CategoryRequest request, Long id) {
        Category category = getEntity(id);
        category.setName(request.name());
        return toResponse(repository.save(category));
    }

    @CacheEvict (cacheNames = "categories", allEntries = true)
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Category getEntity(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }

}