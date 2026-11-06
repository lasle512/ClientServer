package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.StoreRequest;
import ru.university.clothes.dto.StoreResponse;
import ru.university.clothes.model.Store;
import ru.university.clothes.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository repository;

    public List<StoreResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public StoreResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found")));
    }

    public StoreResponse create(StoreRequest request) {
        Store store = Store.builder()
                .address(request.address()).build();

        return toResponse(repository.save(store));
    }

    private StoreResponse toResponse(Store store) {
        return new StoreResponse(store.getId(),
                store.getAddress()
        );
    }

    public StoreResponse update(StoreRequest request, Long id) {
        Store store = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
        store.setAddress(request.address());
        return toResponse(repository.update(store, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
        repository.delete(id);
    }
}
