package com.inventory.inventory_management_system.coupon.service.impl;

import com.inventory.inventory_management_system.coupon.dto.request.ApplyCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.CreateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.UpdateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.response.CouponResponse;
import com.inventory.inventory_management_system.coupon.dto.response.CouponValidationResponse;
import com.inventory.inventory_management_system.coupon.entity.Coupon;
import com.inventory.inventory_management_system.coupon.mapper.CouponMapper;
import com.inventory.inventory_management_system.coupon.repository.CouponRepository;
import com.inventory.inventory_management_system.coupon.service.CouponService;
import com.inventory.inventory_management_system.coupon.specification.CouponSpecification;
import com.inventory.inventory_management_system.coupon.validator.CouponValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;
    private final CouponValidator couponValidator;

    @Override
    public CouponResponse createCoupon(@NonNull CreateCouponRequest request) {
        couponValidator.validateCreate(request);

        Coupon coupon = couponMapper.toEntity(request);
        coupon.setCurrentUses(0);
        coupon.setActive(true);

        return couponMapper.toResponse(couponRepository.save(coupon));
    }

    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCouponById(@NonNull Long id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        return couponMapper.toResponse(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponResponse> getCoupons(String code, Boolean active, @NonNull Pageable pageable) {
        Specification<Coupon> spec = Specification.where(CouponSpecification.hasCodeLike(code))
                .and(CouponSpecification.isActive(active));
        return couponRepository.findAll(spec, pageable).map(couponMapper::toResponse);
    }

    @Override
    public CouponResponse updateCoupon(@NonNull Long id, @NonNull UpdateCouponRequest request) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));

        couponMapper.updateEntityFromDto(request, coupon);
        return couponMapper.toResponse(couponRepository.save(coupon));
    }

    @Override
    public CouponValidationResponse validateAndApplyCoupon(ApplyCouponRequest request) {
        Coupon coupon = couponRepository.findByCode(request.getCode())
                .orElseThrow(() -> new RuntimeException("Coupon code not found: " + request.getCode()));

        try {
            couponValidator.validateApply(coupon, request);
        } catch (Exception e) {
            return CouponValidationResponse.builder()
                    .valid(false)
                    .message(e.getMessage())
                    .discountAmount(BigDecimal.ZERO)
                    .finalTotal(request.getOrderTotal())
                    .build();
        }

        BigDecimal discountAmount;
        if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
            discountAmount = request.getOrderTotal()
                    .multiply(coupon.getDiscountValue())
                    .divide(BigDecimal.valueOf(100));
        } else {
            discountAmount = coupon.getDiscountValue();
        }

        BigDecimal finalTotal = request.getOrderTotal().subtract(discountAmount).max(BigDecimal.ZERO);

        coupon.setCurrentUses(coupon.getCurrentUses() + 1);
        couponRepository.save(coupon);

        return CouponValidationResponse.builder()
                .valid(true)
                .message("Coupon applied successfully")
                .discountAmount(discountAmount)
                .finalTotal(finalTotal)
                .build();
    }

    @Override
    public void deleteCoupon(@NonNull Long id) {
        if (!couponRepository.existsById(id)) {
            throw new RuntimeException("Coupon not found with id: " + id);
        }
        couponRepository.deleteById(id);
    }
}
