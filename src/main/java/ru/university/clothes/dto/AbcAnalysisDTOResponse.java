package ru.university.clothes.dto;

import java.math.BigDecimal;

public record AbcAnalysisDTOResponse(
        Long productId,
        String productTitle,
        BigDecimal totalRevenue,
        String abcClass
) {
}