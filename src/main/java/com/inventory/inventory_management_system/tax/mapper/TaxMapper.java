package com.inventory.inventory_management_system.tax.mapper;

import com.inventory.inventory_management_system.tax.dto.request.TaxRequest;
import com.inventory.inventory_management_system.tax.dto.response.TaxResponse;
import com.inventory.inventory_management_system.tax.entity.Tax;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaxMapper {

    @NonNull Tax toEntity(@NonNull TaxRequest request);

    @NonNull TaxResponse toResponse(@NonNull Tax entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull TaxRequest request, @MappingTarget @NonNull Tax entity);
}