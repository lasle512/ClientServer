package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Category;

public interface CategoryRepository extends
   JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    boolean existsBySerialNumber(String serialNumber);
}
