package ru.university.clothes.dto;

import java.time.LocalDateTime;
import java.util.List;

import ru.university.clothes.model.SaleItem;
import ru.university.clothes.model.Store;

public record SaleResponse(
        Long id,
        Store store,
        List<SaleItem> items,
        LocalDateTime soldAt,
        String cashier
) {

}
