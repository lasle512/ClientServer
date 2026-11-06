package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.SaleRequest;
import ru.university.clothes.dto.SaleResponse;
import ru.university.clothes.model.Sale;
import ru.university.clothes.repository.SaleRepository;

@Service
@RequiredArgsConstructor
public class SaleService {
    private final SaleRepository repository;

    public List<SaleResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SaleResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found")));
    }

    public SaleResponse create(SaleRequest request) {
        Sale sale = Sale.builder()
                .store(request.store())
                .items(request.items())
                .soldAt(request.soldAt())
                .cashier(request.cashier()).build();

        return toResponse(repository.save(sale));
    }

    private SaleResponse toResponse(Sale sale) {
        return new SaleResponse(sale.getId(),
                sale.getStore(),
                sale.getItems(),
                sale.getSoldAt(),
                sale.getCashier()
        );
    }

    public SaleResponse update(SaleRequest request, Long id) {
        Sale sale = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        sale.setStore(request.store());
        sale.setItems(request.items());
        sale.setSoldAt(request.soldAt());
        sale.setCashier(request.cashier());
        return toResponse(repository.update(sale, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        repository.delete(id);
    }
}
