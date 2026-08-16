package com.inventory.inventory_management_system.discount.mapper;

import com.inventory.inventory_management_system.discount.dto.request.CreateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.UpdateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.response.DiscountResponse;
import com.inventory.inventory_management_system.discount.entity.Discount;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiscountMapper {

    @NonNull Discount toEntity(@NonNull CreateDiscountRequest request);

    @NonNull DiscountResponse toResponse(@NonNull Discount entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull UpdateDiscountRequest request, @MappingTarget @NonNull Discount entity);
}