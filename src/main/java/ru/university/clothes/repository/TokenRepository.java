package ru.university.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.university.clothes.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
