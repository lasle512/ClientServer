package ru.university.clothes.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.university.clothes.model.SaleItem;
import ru.university.clothes.model.Store;

public record SaleRequest(
    @NotNull(message = "Store cannot be null") Store store,
    @NotEmpty(message = "Items cannot be empty") List<SaleItem> items,
    @NotNull(message = "SoldAt cannot be null") LocalDateTime soldAt,
    @NotBlank(message = "Cashier cannot be blank") String cashier
) {

}
