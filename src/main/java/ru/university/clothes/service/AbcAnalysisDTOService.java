package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.AbcAnalysisDTORequest;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;
import ru.university.clothes.model.AbcAnalysisDTO;
import ru.university.clothes.repository.AbcAnalysisDTORepository;

@Service
@RequiredArgsConstructor
public class AbcAnalysisDTOService {

    private final AbcAnalysisDTORepository repository;

    public List<AbcAnalysisDTOResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AbcAnalysisDTOResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AbcAnalysisDTO not found")));
    }

    public AbcAnalysisDTOResponse create(AbcAnalysisDTORequest request) {
        AbcAnalysisDTO abcAnalysisDTO = AbcAnalysisDTO.builder()
                .product(request.product())
                .totalRevenue(request.totalRevenue())
                .abcClass(request.abcClass())
                .build();

        return toResponse(repository.save(abcAnalysisDTO));
    }

    private AbcAnalysisDTOResponse toResponse(AbcAnalysisDTO abcAnalysisDTO) {
        return new AbcAnalysisDTOResponse(abcAnalysisDTO.getId(),
                abcAnalysisDTO.getProduct(),
                abcAnalysisDTO.getTotalRevenue(),
                abcAnalysisDTO.getAbcClass()
        );
    }

    public AbcAnalysisDTOResponse update(AbcAnalysisDTORequest request, Long id) {
        AbcAnalysisDTO abcAnalysisDTO = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AbcAnalysisDTO not found"));
        abcAnalysisDTO.setProduct(request.product());
        abcAnalysisDTO.setTotalRevenue(request.totalRevenue());
        abcAnalysisDTO.setAbcClass(request.abcClass());
        return toResponse(repository.update(abcAnalysisDTO, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AbcAnalysisDTO not found"));
        repository.delete(id);
    }

}
