package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.ClothesApi;
import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;
import ru.university.service.ClothesService;

@RequiredArgsConstructor
@RestController
public class ClothesController implements ClothesApi {
    private final ClothesService service;

    public List<ProductResponse> getAll() {
        return service.findAll();
    }

    public ProductResponse get(Long id) {
        return service.findById(id);
    }

    public ProductResponse create(ProductRequest request) {
        return service.create(request);
    }

    public ProductResponse update(ProductRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
