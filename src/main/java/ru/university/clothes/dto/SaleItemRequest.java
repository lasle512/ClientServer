package ru.university.clothes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import ru.university.clothes.model.Product;

public record SaleItemRequest(

        @NotNull(message = "Product cannot be blank")
        Product product,
        @Min(value = 1, message = "Quantity must be greater than 0")
        int quantity,
        @Min(value = 1, message = "Price must be greater than 0")
        int priceAtSaleTime
) {

}
