package ru.university.clothes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.university.clothes.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}