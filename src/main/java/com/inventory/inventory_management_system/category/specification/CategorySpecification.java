package com.inventory.inventory_management_system.category.specification;

import com.inventory.inventory_management_system.category.dto.request.CategoryFilterRequest;
import com.inventory.inventory_management_system.category.entity.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

public class CategorySpecification {

    public static @NonNull Specification<Category> filter(@NonNull CategoryFilterRequest request) {
        return (root, query, cb) -> {
            Specification<Category> spec = Specification.where(null);
            
            if (request.getName() != null && !request.getName().isBlank()) {
                spec = spec.and((r, q, builder) -> 
                    builder.like(builder.lower(r.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getParentId() != null) {
                spec = spec.and((r, q, builder) -> 
                    builder.equal(r.get("parent").get("id"), request.getParentId()));
            }
            
            return spec.toPredicate(root, query, cb);
        };
    }
}
