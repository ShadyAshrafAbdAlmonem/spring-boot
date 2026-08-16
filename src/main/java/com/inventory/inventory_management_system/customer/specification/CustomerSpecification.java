package com.inventory.inventory_management_system.customer.specification;

import com.inventory.inventory_management_system.customer.entity.Customer;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpecification {

    public static Specification<Customer> hasNameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            String pattern = "%" + name.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), pattern),
                    cb.like(cb.lower(root.get("lastName")), pattern)
            );
        };
    }

    public static Specification<Customer> hasEmail(String email) {
        return (root, query, cb) -> (email == null || email.isBlank()) ? null : cb.equal(cb.lower(root.get("email")), email.toLowerCase());
    }

    public static Specification<Customer> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }
}
