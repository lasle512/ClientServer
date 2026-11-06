package ru.university.clothes.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SaleRequest(
    @NotNull(message = "StoreId cannot be null") Long storeId,
    @NotEmpty(message = "Items cannot be empty") List<@Valid SaleItemRequest> items,
    @NotBlank(message = "Cashier cannot be blank") String cashier
) {
}