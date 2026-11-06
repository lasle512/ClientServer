package ru.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Sale;
import ru.university.repository.SaleRepository;

@Service 
@RequiredArgsConstructor 
public class SaleService {

    private final SaleRepository repository;

    public List<Sale> findAll() {
        return repository.findAll();
    }
    
    public Sale create(Sale sale) {
        return repository.save(sale);
    }

}
