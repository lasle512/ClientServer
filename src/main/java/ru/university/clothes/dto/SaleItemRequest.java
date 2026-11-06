package ru.university.clothes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record SaleItemRequest(
        @NotNull(message = "ProductId cannot be null")
        Long productId,
        @Min(value = 1, message = "Quantity must be greater than 0")
        int quantity
) {
}