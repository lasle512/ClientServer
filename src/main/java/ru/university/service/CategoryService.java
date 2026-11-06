package ru.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Category;
import ru.university.repository.CategoryRepository;

@Service 
@RequiredArgsConstructor 
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }

    public Category create(Category category) {
        return repository.save(category);
    }

}
