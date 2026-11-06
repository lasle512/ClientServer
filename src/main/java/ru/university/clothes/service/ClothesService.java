package ru.university.clothes.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.ClothesRequest;
import ru.university.clothes.dto.ClothesResponse;
import ru.university.clothes.model.Product;

import ru.university.clothes.repository.ClothesRepository;

@Service
@RequiredArgsConstructor
public class ClothesService {
    private final ClothesRepository repository;

    public List<ClothesResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ClothesResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothes not found")));
    }

    public ClothesResponse create(ClothesRequest request) {

        Product clothes = Product.builder()
                .title(request.title())
                .category(request.category())
                .supplier(request.supplier())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .lastRestockedAt(request.lastRestockedAt()).build();

        return toResponse(repository.save(clothes));

    }

    private ClothesResponse toResponse(Product clothes) {
        return new ClothesResponse(clothes.getId(),
                clothes.getTitle(),
                clothes.getCategory(),
                clothes.getSupplier(),
                clothes.getPrice(),
                clothes.getStockQuantity(),
                clothes.getLastRestockedAt()
        );
    }

    public ClothesResponse update(ClothesRequest request, Long id) {
        Product clothes = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothes not found"));
        clothes.setTitle(request.title());
        clothes.setCategory(request.category());
        clothes.setSupplier(request.supplier());
        clothes.setPrice(request.price());
        clothes.setStockQuantity(request.stockQuantity());
        clothes.setLastRestockedAt(request.lastRestockedAt());
        return toResponse(repository.update(clothes, id));
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Clothes not found"));
        repository.delete(id);
    }
}
