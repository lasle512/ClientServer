package ru.university.clothes.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.university.clothes.model.Product;

public record AbcAnalysisDTORequest(

        @NotNull(message = "Product cannot be null")
        Product product,
        @NotNull(message = "Total revenue cannot be null")
        @Min(value = 0, message = "Total revenue cannot be less than 0")
        BigDecimal totalRevenue,
        @NotBlank(message = "ABC class cannot be blank")
        String abcClass

) {

}
