package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.university.clothes.model.SaleItem;

@Repository
public class SaleItemRepository implements SaleItemDao {

    private final AtomicLong sequence = new AtomicLong();

    private final Map<Long, SaleItem> storage = new ConcurrentHashMap<>();

    @Override
    public SaleItem save(SaleItem saleItem) {
        if (saleItem.getId() == null) {
            saleItem.setId(sequence.incrementAndGet());
        }
        storage.put(saleItem.getId(), saleItem);
        return saleItem;
    }

    @Override
    public List<SaleItem> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<SaleItem> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public SaleItem update(SaleItem saleItem, Long id) {
        storage.put(id, saleItem);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

}
