package com.inventory.inventory_management_system.product.mapper;

import com.inventory.inventory_management_system.product.dto.request.CreateProductRequest;
import com.inventory.inventory_management_system.product.dto.request.UpdateProductRequest;
import com.inventory.inventory_management_system.product.dto.response.ProductDetailsResponse;
import com.inventory.inventory_management_system.product.dto.response.ProductResponse;
import com.inventory.inventory_management_system.product.dto.response.ProductSummaryResponse;
import com.inventory.inventory_management_system.product.entity.Product;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "qrCode", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    @NonNull
    Product toEntity(@NonNull CreateProductRequest request);

    @NonNull
    ProductResponse toResponse(@NonNull Product entity);

    @NonNull
    ProductDetailsResponse toDetailsResponse(@NonNull Product entity);

    @NonNull
    ProductSummaryResponse toSummaryResponse(@NonNull Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category", source = "category")
    @Mapping(target = "brand", source = "brand")
    void updateEntityFromDto(@NonNull UpdateProductRequest request, @MappingTarget @NonNull Product entity);
}