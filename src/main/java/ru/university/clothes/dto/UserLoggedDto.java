package ru.university.clothes.dto;
import java.util.Set;

public record UserLoggedDto(String username,  Set<String> role, Set<String> permissions) {
}