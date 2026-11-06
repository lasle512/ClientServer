package ru.university.clothes.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(
        Long id,
        Long storeId,
        String storeAddress,
        List<SaleItemResponse> items,
        int total,
        LocalDateTime soldAt,
        String cashier
) {
}