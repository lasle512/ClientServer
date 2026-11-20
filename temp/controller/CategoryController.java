package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.CategoryApi;
import ru.university.clothes.dto.CategoryRequest;
import ru.university.clothes.dto.CategoryResponse;
import ru.university.clothes.service.CategoryService;

@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoryApi {
    private final CategoryService service;

    public List<CategoryResponse> getAll() {
        return service.findAll();
    }

    public CategoryResponse get(Long id) {
        return service.findById(id);
    }

    public CategoryResponse create(CategoryRequest request) {
        return service.create(request);
    }

    public CategoryResponse update(CategoryRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
