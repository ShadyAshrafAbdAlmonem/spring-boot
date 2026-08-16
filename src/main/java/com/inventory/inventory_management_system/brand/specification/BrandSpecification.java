package com.inventory.inventory_management_system.brand.specification;

import com.inventory.inventory_management_system.brand.entity.Brand;
import org.springframework.data.jpa.domain.Specification;

public class BrandSpecification {

    public static Specification<Brand> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Brand> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }
}
