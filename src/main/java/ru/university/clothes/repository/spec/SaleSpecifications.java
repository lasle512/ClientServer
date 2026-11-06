package ru.university.clothes.repository.spec;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import ru.university.clothes.model.Sale;

public class SaleSpecifications {
    public static Specification<Sale> withFilter(
            Long storeId,
            LocalDateTime from,
            LocalDateTime to) {
        return Specification.allOf(
                storeIdEquals(storeId),
                soldFrom(from),
                soldTo(to));
    }

    private static Specification<Sale> storeIdEquals(Long storeId) {
        return (root, query, cb) -> {
            if (storeId == null)
                return null;
            return cb.equal(root.get("store").get("id"), storeId);
        };
    }

    private static Specification<Sale> soldFrom(LocalDateTime from) {
        return (root, query, cb) -> {
            if (from == null)
                return null;
            return cb.greaterThanOrEqualTo(root.get("soldAt"), from);
        };
    }

    private static Specification<Sale> soldTo(LocalDateTime to) {
        return (root, query, cb) -> {
            if (to == null)
                return null;
            return cb.lessThan(root.get("soldAt"), to);
        };
    }
}