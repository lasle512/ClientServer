package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.StoreApi;
import ru.university.clothes.dto.StoreRequest;
import ru.university.clothes.dto.StoreResponse;
import ru.university.clothes.service.StoreService;

@RequiredArgsConstructor
@RestController
public class StoreController implements StoreApi {
    private final StoreService service;

    public List<StoreResponse> getAll() {
        return service.findAll();
    }

    public StoreResponse get(Long id) {
        return service.findById(id);
    }

    public StoreResponse create(StoreRequest request) {
        return service.create(request);
    }

    public StoreResponse update(StoreRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
