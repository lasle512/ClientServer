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
import ru.university.clothes.dto.SaleItemRequest;
import ru.university.clothes.dto.SaleItemResponse;

@RequestMapping("/api/sale-items")
@Tag(name = "Sale Item Controller")
public interface SaleItemApi {

    @GetMapping()
    public List<SaleItemResponse> getAll();

    @GetMapping("/{id}")
    public SaleItemResponse get(
            @PathVariable Long id);

    @PostMapping
    public SaleItemResponse create(
            @RequestBody @Valid SaleItemRequest entity);

    @PutMapping("/{id}")
    public SaleItemResponse update(
            @RequestBody @Valid SaleItemRequest entity, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
