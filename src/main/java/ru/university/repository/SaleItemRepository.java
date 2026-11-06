package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.SaleItem;

public interface SaleItemRepository extends
   JpaRepository<SaleItem, Long>, JpaSpecificationExecutor<SaleItem> {
    boolean existsBySerialNumber(String serialNumber);
}