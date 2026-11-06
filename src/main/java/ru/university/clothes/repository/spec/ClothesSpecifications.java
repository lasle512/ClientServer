package ru.university.clothes.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import ru.university.clothes.model.Product;

public class ClothesSpecifications {
    public static Specification<Product> withFilter(
            Long categoryId,
            Long supplierId,
            Integer maxStock,
            String title) {
        return Specification.allOf(
                categoryIdEquals(categoryId),
                supplierIdEquals(supplierId),
                stockAtMost(maxStock),
                titleContains(title));

    }

    private static Specification<Product> categoryIdEquals(Long categoryId) {

        return (root, query, cb) -> {
            if (categoryId == null)
                return null;
            return cb.equal(root.get("category").get("id"), categoryId);
        };
    }

    private static Specification<Product> supplierIdEquals(Long supplierId) {

        return (root, query, cb) -> {
            if (supplierId == null)
                return null;
            return cb.equal(root.get("supplier").get("id"), supplierId);
        };
    }

    private static Specification<Product> stockAtMost(Integer maxStock) {

        return (root, query, cb) -> {
            if (maxStock == null)
                return null;
            return cb.le(root.get("stockQuantity"), maxStock);
        };
    }

    private static Specification<Product> titleContains(String title) {

        return (root, query, cb) -> {
            if (title == null || title.isBlank())
                return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };

    }
}