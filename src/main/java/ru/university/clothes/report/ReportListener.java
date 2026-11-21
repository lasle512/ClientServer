package ru.university.clothes.report;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.university.clothes.dto.ProductReportRow;
import ru.university.clothes.dto.ReportComplitedEvent;
import ru.university.clothes.dto.ReportData;
import ru.university.clothes.dto.ReportRequestedEvent;
import ru.university.clothes.repository.ProductRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportListener {
    private final ProductRepository productRepository;
    private final ReportStore store;
    private final ApplicationEventPublisher publisher;

    @Async
    @EventListener
    @Transactional(readOnly = true)
    public void onReportRequested(ReportRequestedEvent event) {
        try {
            List<ProductReportRow> rows = productRepository.findReportRows();
            store.save(new ReportData(event.reportId(), "COMPLETED",
                    LocalDateTime.now(), rows.size(), rows));
            publisher.publishEvent(new ReportComplitedEvent(event.reportId(), "COMPLETED"));
        } catch (Exception e) {
            log.error("Отчёт {} не сформирован", event.reportId(), e);
            publisher.publishEvent(new ReportComplitedEvent(event.reportId(), "FAILED"));
        }
    }
}
