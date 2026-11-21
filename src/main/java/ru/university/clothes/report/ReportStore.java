package ru.university.clothes.report;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import ru.university.clothes.dto.ReportData;

@Component
public class ReportStore {
    private final Map<String, ReportData> data = new ConcurrentHashMap<>();

    public void save(ReportData report) { data.put(report.reportId(), report); }
    public Optional<ReportData> find(String id) { return Optional.ofNullable(data.get(id)); }
}

