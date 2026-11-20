package ru.university.clothes.dto;

public record CsvImportRowError(int lineNumber, String rawLine, String message) {

}
