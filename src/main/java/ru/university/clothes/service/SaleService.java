package ru.university.clothes.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;
import ru.university.clothes.dto.SaleItemRequest;
import ru.university.clothes.dto.SaleItemResponse;
import ru.university.clothes.dto.SaleRequest;
import ru.university.clothes.dto.SaleResponse;
import ru.university.clothes.model.Product;
import ru.university.clothes.model.Sale;
import ru.university.clothes.model.SaleItem;
import ru.university.clothes.model.Store;
import ru.university.clothes.repository.ProductRepository;
import ru.university.clothes.repository.SaleRepository;
import ru.university.clothes.repository.StoreRepository;
import ru.university.clothes.repository.spec.SaleSpecifications;

@Slf4j
@Service 
public class SaleService {

    private final SaleRepository repository;
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;
    private final int lowStockThreshold;

    public SaleService(
        SaleRepository repository,
        StoreRepository storeRepository,
        ProductRepository productRepository,
        @Value("${clothes.low-stock-threshold:5}") int lowStockThreshold
    ) {
        this.repository = repository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
        this.lowStockThreshold = lowStockThreshold;
    }

    @Transactional (readOnly = true)
    public Page<SaleResponse> search(Long storeId, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return repository.findAll(SaleSpecifications.withFilter(storeId, from, to), pageable)
                        .map(this::toResponse);
    }

    @Transactional (readOnly = true)
    public SaleResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found")));
    }

    /**
     * Регистрирует продажу и списывает остатки. Если какого-то товара не хватает,
     * исключение откатывает всю транзакцию: ни продажа, ни списания не сохраняются.
     */
    @Transactional
    public SaleResponse create(SaleRequest request) {
        Store store = storeRepository.findById(request.storeId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));

        Sale sale = Sale.builder()
            .store(store)
            .soldAt(LocalDateTime.now())
            .cashier(request.cashier())
            .build();

        for (SaleItemRequest itemRequest : request.items()) {
            Product product = productRepository.findByIdForUpdate(itemRequest.productId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Product not found: " + itemRequest.productId()));

            if (product.getStockQuantity() < itemRequest.quantity()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Not enough stock for product " + product.getId()
                    + ": requested " + itemRequest.quantity() + ", available " + product.getStockQuantity());
            }
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());

            sale.addItem(SaleItem.builder()
                .product(product)
                .quantity(itemRequest.quantity())
                .priceAtSaleTime(product.getPrice())
                .build());
        }

        Sale saved = repository.save(sale);
        saved.getItems().stream()
            .map(SaleItem::getProduct)
            .distinct()
            .filter(product -> product.getStockQuantity() <= lowStockThreshold)
            .forEach(product -> log.warn("Low stock: product {} '{}' has {} left",
                product.getId(), product.getTitle(), product.getStockQuantity()));

        return toResponse(saved);
    }

    private SaleResponse toResponse(Sale sale) {
        var items = sale.getItems().stream()
            .map(item -> new SaleItemResponse(
                item.getId(),
                item.getProduct().getId(),
                item.getProduct().getTitle(),
                item.getQuantity(),
                item.getPriceAtSaleTime()))
            .toList();
        int total = sale.getItems().stream()
            .mapToInt(item -> item.getQuantity() * item.getPriceAtSaleTime())
            .sum();
        return new SaleResponse(
            sale.getId(),
            sale.getStore().getId(),
            sale.getStore().getAddress(),
            items,
            total,
            sale.getSoldAt(),
            sale.getCashier()
        );
    }

}