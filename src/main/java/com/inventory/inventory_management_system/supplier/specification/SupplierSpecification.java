package com.inventory.inventory_management_system.supplier.specification;

import com.inventory.inventory_management_system.supplier.entity.Supplier;
import org.springframework.data.jpa.domain.Specification;

public class SupplierSpecification {

    public static Specification<Supplier> hasNameLike(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<Supplier> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }
}
