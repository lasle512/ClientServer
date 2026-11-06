package ru.university.clothes.dto;

import ru.university.clothes.model.Product;

public record SaleItemResponse(

        Long id,
        Product product,
        int quantity,
        int priceAtSaleTime

) {

}