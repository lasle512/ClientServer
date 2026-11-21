package ru.university.clothes.dto;

import java.time.LocalDate;

public record ProductReportRow(Long id, String title, String category, String supplier,
                               int price, int stockQuantity, LocalDate lastRestockedAt) {}