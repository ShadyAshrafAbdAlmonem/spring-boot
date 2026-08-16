package com.inventory.inventory_management_system.supplier.mapper;

import com.inventory.inventory_management_system.supplier.dto.request.CreateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.request.UpdateSupplierRequest;
import com.inventory.inventory_management_system.supplier.dto.response.SupplierResponse;
import com.inventory.inventory_management_system.supplier.entity.Supplier;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {

    Supplier toEntity(CreateSupplierRequest request);

    SupplierResponse toResponse(Supplier entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateSupplierRequest request, @MappingTarget Supplier entity);
}
