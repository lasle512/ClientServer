package ru.university.clothes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import jakarta.persistence.LockModeType;
import ru.university.clothes.model.Product;

public interface ProductRepository extends
   JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    // блокирует строку товара до конца транзакции, чтобы две продажи не списали один остаток
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdForUpdate(Long id);
}