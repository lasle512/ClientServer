package ru.university.clothes.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<SaleResponse> getAll(Long storeId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return service.search(storeId, from, to, pageable);
    }

    public SaleResponse get(Long id) {
        return service.findById(id);
    }

    public SaleResponse create(SaleRequest request) {
        return service.create(request);
    }

}