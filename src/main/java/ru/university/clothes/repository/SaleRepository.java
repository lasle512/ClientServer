package ru.university.clothes.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Sale;

public interface SaleRepository extends
   JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {

    @EntityGraph(attributePaths = { "store", "items", "items.product" })
    Optional<Sale> findById(Long id);

    @EntityGraph(attributePaths = { "store" })
    Page<Sale> findAll(Specification<Sale> spec, Pageable pageable);
}