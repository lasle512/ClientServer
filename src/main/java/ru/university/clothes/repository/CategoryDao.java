package ru.university.clothes.repository;

import java.util.List;
import java.util.Optional;

import ru.university.clothes.model.Category;

public interface CategoryDao {
    Category save(Category category);
    Category update(Category category, Long id);
    List<Category> findAll();
    void delete(Long id);
    Optional<Category> findById(Long id);
}
