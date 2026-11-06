package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.SaleItemApi;
import ru.university.clothes.dto.SaleItemRequest;
import ru.university.clothes.dto.SaleItemResponse;
import ru.university.clothes.service.SaleItemService;

@RequiredArgsConstructor
@RestController
public class SaleItemController implements SaleItemApi {
    private final SaleItemService service;

    public List<SaleItemResponse> getAll() {
        return service.findAll();
    }

    public SaleItemResponse get(Long id) {
        return service.findById(id);
    }

    public SaleItemResponse create(SaleItemRequest request) {
        return service.create(request);
    }

    public SaleItemResponse update(SaleItemRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
