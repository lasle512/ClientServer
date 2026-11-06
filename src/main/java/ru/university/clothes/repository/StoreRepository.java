package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.university.clothes.model.Store;

@Repository
public class StoreRepository implements StoreDao {
    private final AtomicLong sequence = new AtomicLong();

    private final Map<Long, Store> storage = new ConcurrentHashMap<>();

    @Override
    public Store save(Store store) {
        if (store.getId() == null) {
            store.setId(sequence.incrementAndGet());
        }
        storage.put(store.getId(), store);
        return store;
    }

    @Override
    public List<Store> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Store> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Store update(Store store, Long id) {
        storage.put(id, store);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

}
