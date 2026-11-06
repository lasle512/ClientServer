package ru.university.service;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;
import ru.university.clothes.model.Product;
import ru.university.clothes.model.Category;
import ru.university.repository.CategoryRepository;
import ru.university.repository.ClothesRepository;
import ru.university.repository.spec.ClothesSpecifications;

@Service 
@RequiredArgsConstructor 
public class ClothesService {

    private final ClothesRepository repository;
    private final CategoryRepository categoryRepository;

    @Transactional (readOnly = true)
    public Page<ProductResponse> search(
        Category category,
        String title,
        Pageable pageable
    ) {
        return repository.findAll(ClothesSpecifications.withFilter(category, title), pageable)
                        .map(this::toResponse);
    }

    @Cacheable (cacheNames = "clothesCatalog")
    @Transactional (readOnly = true)
    public List<ProductResponse> findAllAvailable() {
        return repository.findAll(ClothesSpecifications.withFilter(null, null)).stream()
                .map(this::toResponse).toList();
    }

    public ProductResponse findById(Long id) {
        return toResponse(repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found")));
    }

    @CacheEvict (cacheNames = "productCatalog", allEntries = true) 
    public ProductResponse create(ProductRequest request) {
        Category category = categoryRepository
                .findById(request.category().getId()).orElseThrow(() -> new RuntimeException("Category not found"));
    
        Product product = Product.builder()
            .title(request.title())
            .category(request.category())
            .supplier(request.supplier())
            .price(request.price())
            .stockQuantity(request.stockQuantity())
            .lastRestockedAt(request.lastRestockedAt())
            .build();

        return toResponse(repository.save(product));
    }

    @CacheEvict (cacheNames = "productCatalog", allEntries = true)
    public ProductResponse update(ProductRequest request, Long id) {
        Product product = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Product not found"));
        product.setId(id);
        product.setTitle(request.title());
        product.setCategory(request.category());
        product.setSupplier(request.supplier());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());

        return toResponse(repository.save(product));
    }

    @CacheEvict (cacheNames = "productCatalog", allEntries = true)
    public void delete(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        repository.delete(product);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getCategory(), 
            product.getSupplier(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getLastRestockedAt()
        );
    }

}
