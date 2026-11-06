package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.ClothesApi;
import ru.university.clothes.dto.ClothesRequest;
import ru.university.clothes.dto.ClothesResponse;
import ru.university.clothes.service.ClothesService;

@RequiredArgsConstructor
@RestController
public class ClothesController implements ClothesApi {
    private final ClothesService service;

    public List<ClothesResponse> getAll() {
        return service.findAll();
    }

    public ClothesResponse get(Long id) {
        return service.findById(id);
    }

    public ClothesResponse create(ClothesRequest request) {
        return service.create(request);
    }

    public ClothesResponse update(ClothesRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
