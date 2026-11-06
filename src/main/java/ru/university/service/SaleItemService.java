package ru.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.SaleItem;
import ru.university.repository.SaleItemRepository;

@Service 
@RequiredArgsConstructor 
public class SaleItemService {

    private final SaleItemRepository repository;

    public List<SaleItem> findAll() {
        return repository.findAll();
    }

    public SaleItem create(SaleItem saleItem) {
        return repository.save(saleItem);
    }

}
