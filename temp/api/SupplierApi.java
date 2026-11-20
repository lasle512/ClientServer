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
import ru.university.clothes.dto.SupplierRequest;
import ru.university.clothes.dto.SupplierResponse;

@RequestMapping("/api/suppliers")
@Tag(name = "Supplier Controller")
public interface SupplierApi {
        @GetMapping()
        public List<SupplierResponse> getAll();

        @GetMapping("/{id}")
        public SupplierResponse get(
                        @PathVariable Long id);

        @PostMapping
        public SupplierResponse create(
                        @RequestBody @Valid SupplierRequest entity);

        @PutMapping("/{id}")
        public SupplierResponse update(
                        @RequestBody @Valid SupplierRequest entity, @PathVariable Long id);

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id);
}
