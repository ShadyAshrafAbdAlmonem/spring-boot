package com.inventory.inventory_management_system.coupon.specification;

import com.inventory.inventory_management_system.coupon.entity.Coupon;
import org.springframework.data.jpa.domain.Specification;

public class CouponSpecification {

    public static Specification<Coupon> isActive(Boolean active) {
        return (root, query, cb) -> active == null ? null : cb.equal(root.get("active"), active);
    }

    public static Specification<Coupon> hasCodeLike(String code) {
        return (root, query, cb) -> code == null ? null : cb.like(cb.lower(root.get("code")), "%" + code.toLowerCase() + "%");
    }
}
