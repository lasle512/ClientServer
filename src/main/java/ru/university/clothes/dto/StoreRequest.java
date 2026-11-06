package ru.university.clothes.dto;

import jakarta.validation.constraints.NotBlank;

public record StoreRequest(
    @NotBlank(message = "Address cannot be blank") String address
) {

}
