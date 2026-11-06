package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.SaleApi;
import ru.university.clothes.dto.SaleRequest;
import ru.university.clothes.dto.SaleResponse;
import ru.university.clothes.service.SaleService;

@RequiredArgsConstructor
@RestController
public class SaleController implements SaleApi {
    private final SaleService service;

    public List<SaleResponse> getAll() {
        return service.findAll();
    }

    public SaleResponse get(Long id) {
        return service.findById(id);
    }

    public SaleResponse create(SaleRequest request) {
        return service.create(request);
    }

    public SaleResponse update(SaleRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
