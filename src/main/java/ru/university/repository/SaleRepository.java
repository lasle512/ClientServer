package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Sale;

public interface SaleRepository extends
   JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {
    boolean existsBySerialNumber(String serialNumber);
}
