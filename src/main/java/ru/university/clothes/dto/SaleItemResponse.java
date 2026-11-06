package ru.university.clothes.dto;

public record SaleItemResponse(
        Long id,
        Long productId,
        String productTitle,
        int quantity,
        int priceAtSaleTime
) {
}