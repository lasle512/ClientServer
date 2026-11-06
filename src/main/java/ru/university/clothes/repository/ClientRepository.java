package ru.university.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.university.clothes.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}