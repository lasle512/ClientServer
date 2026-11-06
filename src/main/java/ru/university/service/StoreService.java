package ru.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Store;
import ru.university.repository.StoreRepository;

@Service 
@RequiredArgsConstructor 
public class StoreService {

    private final StoreRepository repository;

    public List<Store> findAll() {
        return repository.findAll();
    }

    public Store create(Store store) {
        return repository.save(store);
    }


}
