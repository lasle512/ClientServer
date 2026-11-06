package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.SupplierApi;
import ru.university.clothes.dto.SupplierRequest;
import ru.university.clothes.dto.SupplierResponse;
import ru.university.service.SupplierService;

@RequiredArgsConstructor
@RestController
public class SupplierController implements SupplierApi {
    private final SupplierService service;

    public List<SupplierResponse> getAll() {
        return service.findAll();
    }

    public SupplierResponse get(Long id) {
        return service.findById(id);
    }

    public SupplierResponse create(SupplierRequest request) {
        return service.create(request);
    }

    public SupplierResponse update(SupplierRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
