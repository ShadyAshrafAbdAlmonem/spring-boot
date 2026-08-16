package com.inventory.inventory_management_system.sales.specification;

import com.inventory.inventory_management_system.sales.entity.SalesOrder;
import org.springframework.data.jpa.domain.Specification;

public class SalesSpecification {

    public static Specification<SalesOrder> hasCustomerId(Long customerId) {
        return (root, query, cb) -> customerId == null ? null : cb.equal(root.get("customerId"), customerId);
    }

    public static Specification<SalesOrder> hasStatus(String status) {
        return (root, query, cb) -> (status == null || status.isBlank()) ? null : cb.equal(root.get("status"), status);
    }
}
