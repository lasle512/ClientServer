package ru.university.clothes.dto;


public record LoginResponse(
        boolean isLogged,
        String roles
) {
}
