package com.inventory.inventory_management_system.discount.specification;

import com.inventory.inventory_management_system.discount.entity.Discount;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class DiscountSpecification {

    public static Specification<Discount> hasNameLike(String name) {
        return (root, query, cb) -> (name == null || name.isBlank()) ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Discount> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }

    public static Specification<Discount> isCurrentlyValid() {
        return (root, query, cb) -> {
            LocalDateTime now = LocalDateTime.now();
            return cb.and(
                    cb.equal(root.get("active"), true),
                    cb.lessThanOrEqualTo(root.get("startDate"), now),
                    cb.greaterThanOrEqualTo(root.get("endDate"), now)
            );
        };
    }
}
