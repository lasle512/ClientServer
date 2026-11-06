package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.university.clothes.model.AbcAnalysisDTO;

@Repository
public class AbcAnalysisDTORepository implements AbcAnalysisDTODao {

    private final AtomicLong sequence = new AtomicLong();

    private final Map<Long, AbcAnalysisDTO> storage = new ConcurrentHashMap<>();

    @Override
    public AbcAnalysisDTO save(AbcAnalysisDTO abcAnalysisDTO) {
        if (abcAnalysisDTO.getId() == null) {
            abcAnalysisDTO.setId(sequence.incrementAndGet());
        }
        storage.put(abcAnalysisDTO.getId(), abcAnalysisDTO);
        return abcAnalysisDTO;
    }

    @Override
    public List<AbcAnalysisDTO> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<AbcAnalysisDTO> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public AbcAnalysisDTO update(AbcAnalysisDTO abcAnalysisDTO, Long id) {
        storage.put(id, abcAnalysisDTO);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }


}
