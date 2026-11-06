package ru.university.clothes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;
import ru.university.clothes.model.Category;
import ru.university.clothes.model.Product;
import ru.university.clothes.model.Supplier;
import ru.university.clothes.repository.CategoryRepository;
import ru.university.clothes.repository.ProductRepository;
import ru.university.clothes.repository.SupplierRepository;
import ru.university.clothes.repository.spec.ClothesSpecifications;

@Service 
public class ClothesService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final int lowStockThreshold;

    public ClothesService(
        ProductRepository repository,
        CategoryRepository categoryRepository,
        SupplierRepository supplierRepository,
        @Value("${clothes.low-stock-threshold:5}") int lowStockThreshold
    ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
        this.lowStockThreshold = lowStockThreshold;
    }

    @Transactional (readOnly = true)
    public Page<ProductResponse> search(
        Long categoryId,
        Long supplierId,
        boolean lowStock,
        String title,
        Pageable pageable
    ) {
        Integer maxStock = lowStock ? lowStockThreshold : null;
        return repository.findAll(ClothesSpecifications.withFilter(categoryId, supplierId, maxStock, title), pageable)
                        .map(this::toResponse);
    }

    @Transactional (readOnly = true)
    public ProductResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
            .title(request.title())
            .category(getCategory(request.categoryId()))
            .supplier(getSupplier(request.supplierId()))
            .price(request.price())
            .stockQuantity(request.stockQuantity())
            .lastRestockedAt(request.lastRestockedAt())
            .build();

        return toResponse(repository.save(product));
    }

    @Transactional
    public ProductResponse update(ProductRequest request, Long id) {
        Product product = getEntity(id);
        product.setTitle(request.title());
        product.setCategory(getCategory(request.categoryId()));
        product.setSupplier(getSupplier(request.supplierId()));
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setLastRestockedAt(request.lastRestockedAt());

        return toResponse(repository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        repository.delete(getEntity(id));
    }

    private Product getEntity(Long id) {
        return repository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    private Category getCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    private Supplier getSupplier(Long id) {
        return supplierRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getCategory().getId(),
            product.getCategory().getName(),
            product.getSupplier().getId(),
            product.getSupplier().getName(),
            product.getPrice(),
            product.getStockQuantity(),
            product.getLastRestockedAt()
        );
    }

}