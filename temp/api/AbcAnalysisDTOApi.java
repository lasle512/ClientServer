package ru.university.clothes.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;
import ru.university.clothes.dto.AbcAnalysisDTOResponse;

@RequestMapping("/api/analytics")
@Tag(name = "ABC Analysis Controller")
public interface AbcAnalysisDTOApi {

    @GetMapping("/abc")
    public List<AbcAnalysisDTOResponse> abc();
}