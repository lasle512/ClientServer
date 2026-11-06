package ru.university.clothes.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank(message = "Name cannot be blank") String name
) {

}
