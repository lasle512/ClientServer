package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.Sale;

public interface SaleDao {
    Sale save(Sale sale);
    Sale update(Sale sale, Long id);
    List<Sale> findAll();
    void delete(Long id);
    Optional<Sale> findById(Long id);
}
