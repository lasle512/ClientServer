package ru.university.clothes.dto;

import java.time.LocalDate;

import ru.university.clothes.model.Supplier;
import ru.university.clothes.model.Category;

public record ProductResponse(
    Long id,
    String title,
    Category category,
    Supplier supplier,
    int price,
    int stockQuantity,
    LocalDate lastRestockedAt
) {


}
