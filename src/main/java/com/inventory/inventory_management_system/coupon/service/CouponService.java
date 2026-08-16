package com.inventory.inventory_management_system.coupon.service;

import com.inventory.inventory_management_system.coupon.dto.request.ApplyCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.CreateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.UpdateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.response.CouponResponse;
import com.inventory.inventory_management_system.coupon.dto.response.CouponValidationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface CouponService {
    CouponResponse createCoupon(@NonNull CreateCouponRequest request);
    CouponResponse getCouponById(@NonNull Long id);
    Page<CouponResponse> getCoupons(@NonNull String code, Boolean active, @NonNull Pageable pageable);
    CouponResponse updateCoupon(@NonNull Long id, @NonNull UpdateCouponRequest request);
    CouponValidationResponse validateAndApplyCoupon(@NonNull ApplyCouponRequest request);
    void deleteCoupon(@NonNull Long id);
}
