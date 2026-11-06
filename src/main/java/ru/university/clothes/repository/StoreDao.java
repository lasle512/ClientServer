package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.Store;

public interface StoreDao {
    Store save(Store store);
    Store update(Store store, Long id);
    List<Store> findAll();
    void delete(Long id);
    Optional<Store> findById(Long id);
}
