package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.SaleItem;

public interface SaleItemDao {

    SaleItem save(SaleItem saleItem);

    SaleItem update(SaleItem saleItem, Long id);

    List<SaleItem> findAll();

    void delete(Long id);

    Optional<SaleItem> findById(Long id);

}
