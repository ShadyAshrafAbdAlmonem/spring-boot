package com.inventory.inventory_management_system.inventorycount.mapper;

import com.inventory.inventory_management_system.inventorycount.dto.request.CreateInventoryCountRequest;
import com.inventory.inventory_management_system.inventorycount.dto.response.InventoryCountResponse;
import com.inventory.inventory_management_system.inventorycount.entity.InventoryCount;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryCountMapper {

    InventoryCount toEntity(CreateInventoryCountRequest request);

    InventoryCountResponse toResponse(InventoryCount entity);
}
