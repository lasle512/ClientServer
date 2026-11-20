package ru.university.clothes.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.dto.ProductRequest;
import ru.university.clothes.dto.ProductResponse;
import ru.university.clothes.model.Category;
import ru.university.clothes.model.Product;
import ru.university.clothes.model.Supplier;
import ru.university.clothes.repository.CategoryRepository;
import ru.university.clothes.repository.ProductRepository;
import ru.university.clothes.repository.SupplierRepository;
import ru.university.clothes.repository.spec.ClothesSpecifications;
import ru.university.clothes.storage.StorageService;

@Service 
@RequiredArgsConstructor
public class ClothesService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final StorageService storageService;

    @Transactional (readOnly = true)
    public Page<ProductResponse> search(
        Long categoryId,
        Long supplierId,
        Integer lowStock,
        String title,
        Pageable pageable
    ) {
        return repository.findAll(ClothesSpecifications.withFilter(categoryId, supplierId, lowStock, title), pageable)
                        .map(this::toResponse);
    }

    @Transactional (readOnly = true)
    public ProductResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {

        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Supplier supplier = supplierRepository.findById(request.supplierId()).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Supplier not found"));

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

    @Transactional 
    @CacheEvict(cacheNames = "ProductCatalog", allEntries = true)
    public String uploadPhoto(Long id, MultipartFile file){
        Product product = repository.findById(id).orElseThrow(() -> new RuntimeException("product not found "));
        String url = storageService.store("product-photos", file);
        product.setPhotoUrl(url);
        return url;
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
            product.getLastRestockedAt(),
            product.getPhotoUrl()
        );
    }

}