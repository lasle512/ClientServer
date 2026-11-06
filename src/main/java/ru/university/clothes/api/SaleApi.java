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
import ru.university.clothes.dto.SaleRequest;
import ru.university.clothes.dto.SaleResponse;

@RequestMapping("/api/sales")
@Tag(name = "Sale Controller")
public interface SaleApi {

    @GetMapping()
    public List<SaleResponse> getAll();

    @GetMapping("/{id}")
    public SaleResponse get(
            @PathVariable Long id);

    @PostMapping
    public SaleResponse create(
            @RequestBody @Valid SaleRequest entity);

    @PutMapping("/{id}")
    public SaleResponse update(
            @RequestBody @Valid SaleRequest entity, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
