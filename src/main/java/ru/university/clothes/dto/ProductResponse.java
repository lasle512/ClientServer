package ru.university.clothes.dto;

import java.time.LocalDate;

public record ProductResponse(
    Long id,
    String title,
    Long categoryId,
    String categoryName,
    Long supplierId,
    String supplierName,
    int price,
    int stockQuantity,
    LocalDate lastRestockedAt
) {
}