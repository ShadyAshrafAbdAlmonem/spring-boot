package com.inventory.inventory_management_system.brand.mapper;

import com.inventory.inventory_management_system.brand.dto.request.CreateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.request.UpdateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.response.BrandResponse;
import com.inventory.inventory_management_system.brand.entity.Brand;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    @NonNull Brand toEntity(@NonNull CreateBrandRequest request);

    @NonNull BrandResponse toResponse(@NonNull Brand entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull UpdateBrandRequest request, @MappingTarget @NonNull Brand entity);
}