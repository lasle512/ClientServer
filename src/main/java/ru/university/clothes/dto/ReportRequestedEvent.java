package ru.university.clothes.dto;

import java.time.LocalDateTime;

public record ReportRequestedEvent(String reportId, LocalDateTime requestedAt) {

}
