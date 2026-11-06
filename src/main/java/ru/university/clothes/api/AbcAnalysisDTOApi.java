package ru.university.clothes.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.university.clothes.dto.AbcAnalysisDTORequest;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;

@RequestMapping("/api/abc-analysis")
@Tag(name = "ABC Analysis Controller")
public interface AbcAnalysisDTOApi {

    @GetMapping()
    public List<AbcAnalysisDTOResponse> getAll();

    @GetMapping("/{id}")
    public AbcAnalysisDTOResponse get(
            @PathVariable Long id);

    @PostMapping
    public AbcAnalysisDTOResponse create(
            @RequestBody @Valid AbcAnalysisDTORequest entity);

    @PutMapping("/{id}")
    public AbcAnalysisDTOResponse update(
            @RequestBody @Valid AbcAnalysisDTORequest entity, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id);


}
