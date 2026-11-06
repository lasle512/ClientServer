package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.SupplierRequest;
import ru.university.clothes.dto.SupplierResponse;
import ru.university.clothes.model.Supplier;
import ru.university.clothes.repository.SupplierRepository;

@Service 
@RequiredArgsConstructor 
public class SupplierService {

    private final SupplierRepository repository;

    public List<SupplierResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SupplierResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    public SupplierResponse create(SupplierRequest request) {
        Supplier supplier = Supplier.builder()
            .name(request.name())
            .contactInfo(request.contactInfo())
            .build();
        return toResponse(repository.save(supplier));
    }

    public SupplierResponse update(SupplierRequest request, Long id) {
        Supplier supplier = getEntity(id);
        supplier.setName(request.name());
        supplier.setContactInfo(request.contactInfo());
        return toResponse(repository.save(supplier));
    }

    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Supplier getEntity(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
    }

    private SupplierResponse toResponse(Supplier supplier) {
        return new SupplierResponse(supplier.getId(), supplier.getName(), supplier.getContactInfo());
    }

}