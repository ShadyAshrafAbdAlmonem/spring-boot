package com.inventory.inventory_management_system.coupon.mapper;

import com.inventory.inventory_management_system.coupon.dto.request.CreateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.request.UpdateCouponRequest;
import com.inventory.inventory_management_system.coupon.dto.response.CouponResponse;
import com.inventory.inventory_management_system.coupon.entity.Coupon;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponMapper {

    @NonNull Coupon toEntity(@NonNull CreateCouponRequest request);

    @NonNull CouponResponse toResponse(@NonNull Coupon entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull UpdateCouponRequest request, @MappingTarget @NonNull Coupon entity);
}