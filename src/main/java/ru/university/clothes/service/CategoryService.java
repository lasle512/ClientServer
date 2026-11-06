package ru.university.clothes.service;

import java.util.List;

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

    public List<CategoryResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CategoryResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")));
    }

    public CategoryResponse create(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name()).build();

        return toResponse(repository.save(category));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(),
                category.getName()
        );
    }

    public CategoryResponse update(CategoryRequest request, Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setName(request.name());
        return toResponse(repository.update(category, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        repository.delete(id);
    }
}
