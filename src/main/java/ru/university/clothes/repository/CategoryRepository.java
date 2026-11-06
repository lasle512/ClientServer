package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.university.clothes.model.Category;

@Repository
public class CategoryRepository implements CategoryDao {
    private final AtomicLong sequence = new AtomicLong();

    private final Map<Long, Category> storage = new ConcurrentHashMap<>();

    @Override
    public Category save(Category category) {
        if (category.getId() == null) {
            category.setId(sequence.incrementAndGet());
        }
        storage.put(category.getId(), category);
        return category;
    }

    @Override
    public List<Category> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Category> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Category update(Category category, Long id) {
        storage.put(id, category);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

}
