package ru.university.clothes.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.AbcAnalysisDTOApi;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;
import ru.university.clothes.service.AbcAnalysisDTOService;

@RequiredArgsConstructor
@RestController
public class AbcAnalysisDTOController implements AbcAnalysisDTOApi {
    private final AbcAnalysisDTOService service;

    public List<AbcAnalysisDTOResponse> abc() {
        return service.analyze();
    }

}