package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Product;

public interface ClientRepository extends
   JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsBySerialNumber(String serialNumber);
}
