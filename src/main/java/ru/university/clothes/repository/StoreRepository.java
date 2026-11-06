package ru.university.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.university.clothes.model.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {
}