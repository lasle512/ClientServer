package ru.university.clothes.report;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.university.clothes.model.Category;
import ru.university.clothes.model.Product;
import ru.university.clothes.model.Supplier;
import ru.university.clothes.repository.CategoryRepository;
import ru.university.clothes.repository.ProductRepository;
import ru.university.clothes.repository.SupplierRepository;

@Component
@RequiredArgsConstructor
public class EquipmentRowImporter {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void importRow(String title, String categoryName, String supplierName, String contact_info,
                        Integer price, Integer stockQuantity, LocalDate lastRestockedAt) {
        Supplier supplier = supplierRepository.findByName(supplierName).orElseGet(
                () -> supplierRepository.save(
                        Supplier.builder().name(supplierName)
                        .contactInfo(contact_info)
                        .build()));
        Category category = categoryRepository.findByName(categoryName).orElseGet(
                () -> categoryRepository.save(
                        Category.builder().name(categoryName).build()));
        repository.save(Product.builder()
                .title(title).category(category)
                .supplier(supplier).price(price)
                .stockQuantity(stockQuantity)
                .lastRestockedAt(lastRestockedAt)
                .build());
        }
}
