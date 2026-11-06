package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.SaleItemRequest;
import ru.university.clothes.dto.SaleItemResponse;
import ru.university.clothes.model.SaleItem;
import ru.university.clothes.repository.SaleItemRepository;

@Service
@RequiredArgsConstructor
public class SaleItemService {
    private final SaleItemRepository repository;

    public List<SaleItemResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SaleItemResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SaleItem not found")));
    }

    public SaleItemResponse create(SaleItemRequest request) {
        SaleItem saleItem = SaleItem.builder()
                .product(request.product())
                .quantity(request.quantity())
                .priceAtSaleTime(request.priceAtSaleTime()).build();

        return toResponse(repository.save(saleItem));
    }

    private SaleItemResponse toResponse(SaleItem saleItem) {
        return new SaleItemResponse(saleItem.getId(),
                saleItem.getProduct(),
                saleItem.getQuantity(),
                saleItem.getPriceAtSaleTime()
        );
    }

    public SaleItemResponse update(SaleItemRequest request, Long id) {
        SaleItem saleItem = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SaleItem not found"));
        saleItem.setProduct(request.product());
        saleItem.setQuantity(request.quantity());
        saleItem.setPriceAtSaleTime(request.priceAtSaleTime());
        return toResponse(repository.update(saleItem, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SaleItem not found"));
        repository.delete(id);
    }

}
