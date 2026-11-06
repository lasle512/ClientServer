package ru.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Supplier;
import ru.university.repository.SupplierRepository;

@Service 
@RequiredArgsConstructor 
public class SupplierService {

    private final SupplierRepository repository;

    public List<Supplier> findAll() {
        return repository.findAll();
    }

    public Supplier create(Supplier supplier) {
        return repository.save(supplier);
    }

}
