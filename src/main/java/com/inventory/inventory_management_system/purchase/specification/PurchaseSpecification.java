package com.inventory.inventory_management_system.purchase.specification;

import com.inventory.inventory_management_system.purchase.entity.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

public class PurchaseSpecification {

    public static Specification<PurchaseOrder> hasSupplier(Long supplierId) {
        return (root, query, cb) -> supplierId == null ? null : cb.equal(root.get("supplierId"), supplierId);
    }

    public static Specification<PurchaseOrder> hasStatus(String status) {
        return (root, query, cb) -> (status == null || status.isBlank()) ? null : cb.equal(root.get("status"), status);
    }
}