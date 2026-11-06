package ru.university.clothes.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.university.clothes.model.Category;
import ru.university.clothes.model.Supplier;

public record ClothesRequest(
    @NotBlank(message = "Title cannot be blank") String title,
    @NotNull(message = "Category cannot be null") Category category,
    @NotNull(message = "Supplier cannot be null") Supplier supplier,
    @Min(value = 0, message = "price cannot be lower than 0") int price,
    @Min(value = 0, message = "quantity cannot be lower than 0") int stockQuantity,
    @NotNull(message = "LastRestockedAt cannot be null") LocalDate lastRestockedAt
) {

}
