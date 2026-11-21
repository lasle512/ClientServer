package ru.university.clothes.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.ReportRequestedEvent;
import ru.university.clothes.report.ReportStore;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ApplicationEventPublisher publisher;
    private final ReportStore store;

    @PostMapping
    public Map<String, String> request() {
        String id = UUID.randomUUID().toString();
        publisher.publishEvent(new ReportRequestedEvent(id, LocalDateTime.now()));
        return Map.of("reportId", id, "status", "PENDING");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return store.find(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.accepted()
                        .body(Map.of("reportId", id, "status", "PENDING")));
    }
}