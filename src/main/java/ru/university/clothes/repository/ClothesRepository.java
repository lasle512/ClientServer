package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Product;
import ru.university.clothes.service.IdGeneratorService;

@RequiredArgsConstructor
@Repository 
public class ClothesRepository implements ClothesDao {
    private final IdGeneratorService idGeneratorService;

    private final Map<Long, Product> storage = new ConcurrentHashMap<>();

    @Override
    public Product save(Product clothes) {
        if (clothes.getId() == null) {
            clothes.setId(idGeneratorService.next());
        }
        storage.put(clothes.getId(), clothes);
        return clothes;
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Product update(Product clothes, Long id) {
        storage.put(id, clothes);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    
    }

}
