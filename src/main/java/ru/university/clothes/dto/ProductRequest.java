package ru.university.clothes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
    @NotBlank(message = "Title cannot be blank") String title,
    @NotNull(message = "CategoryId cannot be null") Long categoryId,
    @NotNull(message = "SupplierId cannot be null") Long supplierId,
    @Min(value = 0, message = "price cannot be lower than 0") int price,
    @Min(value = 0, message = "quantity cannot be lower than 0") int stockQuantity,
    @NotNull(message = "LastRestockedAt cannot be null") LocalDate lastRestockedAt
) {
}