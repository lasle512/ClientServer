package ru.university.repository.spec;

import org.springframework.data.jpa.domain.Specification;

import ru.university.clothes.model.Category;
import ru.university.clothes.model.Product;

public class ClothesSpecifications {
    public static Specification<Product> withFilter(
            Category category,
            String title) {
        return Specification.allOf(
                categoryIdEquals(category),
                titleContains(title));

    }

    private static Specification<Product> categoryIdEquals(Category category) {

        return (root, query, cb) -> {
            if (category == null)
                return null;
            return cb.equal(root.get("category").get("id"), category.getId());
        };
    }

    private static Specification<Product> titleContains(String title) {

        return (root, query, cb) -> {
            if (title == null || title.isBlank() || title.trim().isEmpty())
                return null;
            return cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };

    }
}
