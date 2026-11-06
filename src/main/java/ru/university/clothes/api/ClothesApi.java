package ru.university.clothes.api;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;

@RequestMapping("/api/products")
@Tag(name = "Clothes Controller")
public interface ClothesApi {

        @GetMapping()
        public Page<ProductResponse> getAll(
                        @RequestParam(required = false) Long categoryId,
                        @RequestParam(required = false) Long supplierId,
                        @RequestParam(defaultValue = "false") boolean lowStock,
                        @RequestParam(required = false) String title,
                        @ParameterObject @PageableDefault(size = 10, sort = "title") Pageable pageable);

        @GetMapping("/{id}")
        public ProductResponse get(
                        @PathVariable Long id);

        @PostMapping
        public ProductResponse create(
                        @RequestBody @Valid ProductRequest entity);

        @PutMapping("/{id}")
        public ProductResponse update(
                        @RequestBody @Valid ProductRequest entity, @PathVariable Long id);

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id);
}