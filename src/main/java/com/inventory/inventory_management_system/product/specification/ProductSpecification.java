package com.inventory.inventory_management_system.product.specification;

import com.inventory.inventory_management_system.product.dto.request.ProductFilterRequest;
import com.inventory.inventory_management_system.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static @NonNull Specification<Product> filterProducts(@NonNull ProductFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getName() != null && !filter.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
            }
            if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
                predicates.add(cb.equal(root.get("category"), filter.getCategory()));
            }
            if (filter.getBrand() != null && !filter.getBrand().isBlank()) {
                predicates.add(cb.equal(root.get("brand"), filter.getBrand()));
            }
            if (filter.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filter.getActive()));
            }
            if (Boolean.TRUE.equals(filter.getLowStock())) {
                predicates.add(cb.lessThanOrEqualTo(root.get("quantity"), root.get("minQuantity")));
            }
            if (filter.getMinPrice() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice()));
            }
            if (filter.getMaxPrice() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
