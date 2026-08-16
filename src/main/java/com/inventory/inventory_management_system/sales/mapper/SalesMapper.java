package com.inventory.inventory_management_system.sales.mapper;

import com.inventory.inventory_management_system.sales.dto.request.CreateSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.response.SalesOrderItemResponse;
import com.inventory.inventory_management_system.sales.dto.response.SalesOrderResponse;
import com.inventory.inventory_management_system.sales.entity.SalesOrder;
import com.inventory.inventory_management_system.sales.entity.SalesOrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SalesMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderNumber", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    SalesOrder toEntity(CreateSalesOrderRequest request);

    SalesOrderResponse toResponse(SalesOrder entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "salesOrder", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    SalesOrderItem toItemEntity(CreateSalesOrderRequest.SalesOrderItemRequest itemRequest);

    SalesOrderItemResponse toItemResponse(SalesOrderItem item);
}
