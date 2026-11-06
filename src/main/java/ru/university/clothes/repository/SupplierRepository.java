package ru.university.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.university.clothes.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}