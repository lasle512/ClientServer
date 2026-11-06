package ru.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import ru.university.clothes.model.Store;

public interface StoreRepository extends
   JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {
    boolean existsBySerialNumber(String serialNumber);
}
