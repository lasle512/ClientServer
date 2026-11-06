package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Supplier;

public interface SupplierRepository extends
   JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    boolean existsBySerialNumber(String serialNumber);
}
