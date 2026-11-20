package ru.university.clothes.api;

import java.time.LocalDateTime;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.university.clothes.dto.SaleRequest;
import ru.university.clothes.dto.SaleResponse;

@RequestMapping("/api/sales")
@Tag(name = "Sale Controller")
public interface SaleApi {

    @GetMapping()
    public Page<SaleResponse> getAll(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @ParameterObject @PageableDefault(size = 20, sort = "soldAt", direction = Sort.Direction.DESC) Pageable pageable);

    @GetMapping("/{id}")
    public SaleResponse get(
            @PathVariable Long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SaleResponse create(
            @RequestBody @Valid SaleRequest entity);
}