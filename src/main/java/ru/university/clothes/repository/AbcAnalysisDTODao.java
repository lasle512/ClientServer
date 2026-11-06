package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.AbcAnalysisDTO;

public interface AbcAnalysisDTODao {
    AbcAnalysisDTO save(AbcAnalysisDTO abcAnalysisDTO);

    AbcAnalysisDTO update(AbcAnalysisDTO abcAnalysisDTO, Long id);

    List<AbcAnalysisDTO> findAll();

    void delete(Long id);

    Optional<AbcAnalysisDTO> findById(Long id);
}
