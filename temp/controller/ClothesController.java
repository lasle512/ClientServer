package ru.university.clothes.controller;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.api.ClothesApi;
import ru.university.clothes.dto.CsvImportResult;
import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;
import ru.university.clothes.report.EquipmentImportService;
import ru.university.clothes.service.ClothesService;

@RequiredArgsConstructor
@RestController
public class ClothesController implements ClothesApi {
    private final ClothesService service;
    private final EquipmentImportService importService;

    public ProductResponse get(Long id) {
        return service.findById(id);
    }

    public ProductResponse create(ProductRequest request) {
        return service.create(request);
    }

    public ProductResponse update(ProductRequest request, Long id) {
        return service.update(request, id);
    }

    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    
    public String uploadPhoto(Long id, MultipartFile file) {
        return service.uploadPhoto(id, file);
    }

    public CsvImportResult importCsv(MultipartFile file) {
        try {
            return importService.importCsv(file.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public Page<ProductResponse> getAll(Long categoryId, Long supplierId, boolean lowStock, String title,
            String photoUrl, Integer stockQuantity, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }



}