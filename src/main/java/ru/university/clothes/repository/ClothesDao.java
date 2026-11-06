package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.Product;
public interface ClothesDao {
    Product save(Product clothes);
    Product update(Product clothes, Long id);
    List<Product> findAll();
    void delete(Long id);
    Optional<Product> findById(Long id);
}
