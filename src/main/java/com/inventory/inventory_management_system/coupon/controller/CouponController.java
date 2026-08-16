package com.inventory.inventory_management_system.coupon.controller;

import com.inventory.inventory_management_system.coupon.dto.request.ApplyCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.CreateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.UpdateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.response.CouponResponse;
import com.inventory.inventory_management_system.coupon.dto.response.CouponValidationResponse;
import com.inventory.inventory_management_system.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody @NonNull CreateCouponRequest request) {
        return new ResponseEntity<>(couponService.createCoupon(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(couponService.getCouponById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CouponResponse>> getAll(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        return ResponseEntity.ok(couponService.getCoupons(code, active, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponse> update(@PathVariable @NonNull Long id, @RequestBody @NonNull UpdateCouponRequest request) {
        return ResponseEntity.ok(couponService.updateCoupon(id, request));
    }

    @PostMapping("/apply")
    public ResponseEntity<CouponValidationResponse> applyCoupon(@Valid @RequestBody @NonNull ApplyCouponRequest request) {
        return ResponseEntity.ok(couponService.validateAndApplyCoupon(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }
}