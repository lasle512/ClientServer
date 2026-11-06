package ru.university.clothes.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.university.clothes.model.Sale;

@Repository
public class SaleRepository implements SaleDao {
    private final AtomicLong sequence = new AtomicLong();

    private final Map<Long, Sale> storage = new ConcurrentHashMap<>();

    @Override
    public Sale save(Sale sale) {
        if (sale.getId() == null) {
            sale.setId(sequence.incrementAndGet());
        }
        storage.put(sale.getId(), sale);
        return sale;
    }

    @Override
    public List<Sale> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Sale update(Sale sale, Long id) {
        storage.put(id, sale);
        return storage.get(id);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

}
