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
import ru.university.clothes.dto.ClothesRequest;
import ru.university.clothes.dto.ClothesResponse;

@RequestMapping("/api/clothes")
@Tag(name = "Clothes Controller")
public interface ClothesApi {
        @GetMapping()
        public List<ClothesResponse> getAll();

        @GetMapping("/{id}")
        public ClothesResponse get(
                        @PathVariable Long id);

        @PostMapping
        public ClothesResponse create(
                        @RequestBody @Valid ClothesRequest entity);

        @PutMapping("/{id}")
        public ClothesResponse update(
                        @RequestBody @Valid ClothesRequest entity, @PathVariable Long id);

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id);
}
