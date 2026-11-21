package ru.university.clothes.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ReportData(String reportId, String status, LocalDateTime generatedAt,
                         int total, List<ProductReportRow> products) {}