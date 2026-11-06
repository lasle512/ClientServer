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
import ru.university.clothes.dto.StoreRequest;
import ru.university.clothes.dto.StoreResponse;

@RequestMapping("/api/stores")
@Tag(name = "Store Controller")
public interface StoreApi {

    @GetMapping()
    public List<StoreResponse> getAll();

    @GetMapping("/{id}")
    public StoreResponse get(
            @PathVariable Long id);

    @PostMapping
    public StoreResponse create(
            @RequestBody @Valid StoreRequest entity);

    @PutMapping("/{id}")
    public StoreResponse update(
            @RequestBody @Valid StoreRequest entity, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id);
}
