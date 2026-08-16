package com.inventory.inventory_management_system.category.mapper;

import com.inventory.inventory_management_system.category.dto.request.CreateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.request.UpdateCategoryRequest;
import com.inventory.inventory_management_system.category.dto.response.CategoryDetailsResponse;
import com.inventory.inventory_management_system.category.dto.response.CategoryResponse;
import com.inventory.inventory_management_system.category.entity.Category;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @NonNull Category toEntity(@NonNull CreateCategoryRequest request);

    @Mapping(target = "parentId", source = "parent.id")
    @NonNull CategoryResponse toResponse(@NonNull Category entity);

    @Mapping(target = "parent", source = "parent")
    @NonNull CategoryDetailsResponse toDetailsResponse(@NonNull Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "parent", ignore = true)
    void updateEntityFromDto(@NonNull UpdateCategoryRequest request, @MappingTarget @NonNull Category entity);
}