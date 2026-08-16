package com.inventory.inventory_management_system.productimage.mapper;

import com.inventory.inventory_management_system.productimage.dto.response.ProductImageResponse;
import com.inventory.inventory_management_system.productimage.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {
    ProductImageResponse toResponse(ProductImage entity);
}