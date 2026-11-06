package ru.university.clothes.dto;

import jakarta.validation.constraints.NotBlank;

public record SupplierRequest(
    @NotBlank(message = "Name cannot be blank") String name,
    @NotBlank(message = "ContactInfo cannot be blank") String contactInfo
) {

}
