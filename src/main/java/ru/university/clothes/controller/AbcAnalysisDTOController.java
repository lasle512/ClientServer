package ru.university.clothes.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.AbcAnalysisDTOApi;
import ru.university.clothes.dto.AbcAnalysisDTORequest;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;
import ru.university.clothes.service.AbcAnalysisDTOService;

@RequiredArgsConstructor
@RestController
public class AbcAnalysisDTOController implements AbcAnalysisDTOApi {
    private final AbcAnalysisDTOService service;

    public List<AbcAnalysisDTOResponse> getAll() {
        return service.findAll();
    }

    public AbcAnalysisDTOResponse get(Long id) {
        return service.findById(id);
    }

    public AbcAnalysisDTOResponse create(AbcAnalysisDTORequest request) {
        return service.create(request);
    }

    public AbcAnalysisDTOResponse update(AbcAnalysisDTORequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
