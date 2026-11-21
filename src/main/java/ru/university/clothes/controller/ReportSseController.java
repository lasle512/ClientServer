package ru.university.clothes.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.university.clothes.dto.ReportComplitedEvent;
import ru.university.clothes.report.ReportStore;

@Slf4j
@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportSseController {
    private final ReportStore store;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(e -> emitters.remove(emitter));

        try {
            // сразу отправляем событие, чтобы соединение считалось открытым
            emitter.send(SseEmitter.event().name("connected").data("ok"));
        } catch (IOException e) {
            emitters.remove(emitter);
        }
        return emitter;
    }

    @EventListener
    public void onReportCompleted(ReportComplitedEvent event) {
        String name;
        Object payload;

        if ("COMPLETED".equals(event.status())) {
            name = "report-ready";
            payload = store.find(event.reportId()).orElse(null);
        } else {
            name = "report-failed";
            payload = Map.of("reportId", event.reportId(), "status", event.status());
        }
        if (payload == null) {
            log.warn("Для отчёта {} нет данных в ReportStore", event.reportId());
            return;
        }

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().id(event.reportId()).name(name).data(payload));
            } catch (Exception e) {
                emitters.remove(emitter);
            }
        }
    }
}