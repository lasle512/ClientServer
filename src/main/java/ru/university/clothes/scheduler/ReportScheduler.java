package ru.university.clothes.scheduler;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.university.clothes.dto.ReportRequestedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportScheduler {

    private final ApplicationEventPublisher eventPublisher;

    // @Scheduled (cron = "0 30 6 * * 1-5")
    @Scheduled(fixedRate = 1000) // 5 секунд
    public void scheduleReportGeneration() {
        String reportId = UUID.randomUUID().toString();
        
        log.info("[Thread : {}] report initiated {}",
                Thread.currentThread().getName(),
                reportId);

        eventPublisher.publishEvent(new ReportRequestedEvent(reportId, LocalDateTime.now()));
    }
}
