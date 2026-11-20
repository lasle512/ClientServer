package ru.university.clothes.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.university.clothes.dto.CategoryRequest;
import ru.university.clothes.dto.CategoryResponse;

@RequestMapping("/api/categories")
@Tag(name = "Category Controller")
public interface CategoryApi {

    @GetMapping()
    public List<CategoryResponse> getAll();

    @GetMapping("/{id}")
    public CategoryResponse get(
            @PathVariable Long id);

    @PostMapping
    public CategoryResponse create(
            @RequestBody @Valid CategoryRequest entity);

    @PutMapping("/{id}")
    public CategoryResponse update(
            @RequestBody @Valid CategoryRequest entity, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
