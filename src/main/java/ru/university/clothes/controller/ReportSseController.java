package ru.university.clothes.controller;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;
import ru.university.clothes.dto.ReportComplitedEvent;

@Slf4j
@RestController
@RequestMapping("/api/reports")
public class ReportSseController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();


    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));

        return emitter;
    }

    @EventListener
    public void onReportCompleted(ReportComplitedEvent event) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().id(event.reportId())
                        .name("report-ready")
                        .data(event));
            } catch (Exception e) {
                emitters.remove(emitter);

            }
        }
    }

}
