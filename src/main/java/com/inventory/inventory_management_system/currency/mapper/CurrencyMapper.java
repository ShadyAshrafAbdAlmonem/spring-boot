package com.inventory.inventory_management_system.currency.mapper;

import com.inventory.inventory_management_system.currency.dto.request.CurrencyRequest;
import com.inventory.inventory_management_system.currency.dto.response.CurrencyResponse;
import com.inventory.inventory_management_system.currency.entity.Currency;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    @NonNull Currency toEntity(@NonNull CurrencyRequest request);

    @NonNull CurrencyResponse toResponse(@NonNull Currency entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull CurrencyRequest request, @MappingTarget @NonNull Currency entity);
}