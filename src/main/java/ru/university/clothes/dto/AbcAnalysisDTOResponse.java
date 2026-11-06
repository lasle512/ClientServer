package ru.university.clothes.dto;

import java.math.BigDecimal;

import ru.university.clothes.model.Product;

public record AbcAnalysisDTOResponse(

        Long id,
        Product product,
        BigDecimal totalRevenue,
        String abcClass

) {

}
