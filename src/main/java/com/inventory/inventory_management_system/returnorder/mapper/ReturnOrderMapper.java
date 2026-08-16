package com.inventory.inventory_management_system.returnorder.mapper;

import com.inventory.inventory_management_system.returnorder.dto.request.CreateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderDetailsResponse;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderResponse;
import com.inventory.inventory_management_system.returnorder.entity.ReturnOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReturnOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "returnNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    ReturnOrder toEntity(CreateReturnOrderRequest request);

    ReturnOrderResponse toResponse(ReturnOrder entity);

    ReturnOrderDetailsResponse toDetailsResponse(ReturnOrder entity);
}
