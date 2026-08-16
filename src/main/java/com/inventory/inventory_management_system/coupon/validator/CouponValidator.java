package com.inventory.inventory_management_system.coupon.validator;

import com.inventory.inventory_management_system.coupon.dto.request.ApplyCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.CreateCouponRequest;
import com.inventory.inventory_management_system.coupon.entity.Coupon;
import com.inventory.inventory_management_system.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CouponValidator {

    private final CouponRepository couponRepository;

    public void validateCreate(CreateCouponRequest request) {
        if (couponRepository.existsByCode(request.getCode())) {
            throw new IllegalArgumentException("Coupon code already exists: " + request.getCode());
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    public void validateApply(Coupon coupon, ApplyCouponRequest request) {
        if (!Boolean.TRUE.equals(coupon.getActive())) {
            throw new IllegalStateException("Coupon is not active");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getStartDate()) || now.isAfter(coupon.getEndDate())) {
            throw new IllegalStateException("Coupon is expired or not yet valid");
        }

        if (coupon.getMaxUses() != null && coupon.getCurrentUses() >= coupon.getMaxUses()) {
            throw new IllegalStateException("Coupon maximum usage limit reached");
        }

        if (coupon.getMinOrderAmount() != null && request.getOrderTotal().compareTo(coupon.getMinOrderAmount()) < 0) {
            throw new IllegalStateException("Order total does not meet the minimum amount requirement: " + coupon.getMinOrderAmount());
        }
    }
}
