package ru.university.clothes.dto;

import java.time.LocalDate;
import ru.university.clothes.model.Category;
import ru.university.clothes.model.Supplier;

public record ClothesResponse(
    Long id,
    String title,
    Category category,
    Supplier supplier,
    int price,
    int stockQuantity,
    LocalDate lastRestockedAt
) {

}
