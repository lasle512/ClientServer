package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.Supplier;

public interface SupplierDao {
    Supplier save(Supplier supplier);
    Supplier update(Supplier supplier, Long id);
    List<Supplier> findAll();
    void delete(Long id);
    Optional<Supplier> findById(Long id);
}
