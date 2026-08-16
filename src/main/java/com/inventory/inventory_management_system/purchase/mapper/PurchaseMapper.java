package com.inventory.inventory_management_system.purchase.mapper;

import com.inventory.inventory_management_system.purchase.dto.request.CreatePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.response.PurchaseOrderItemResponse;
import com.inventory.inventory_management_system.purchase.dto.response.PurchaseOrderResponse;
import com.inventory.inventory_management_system.purchase.entity.PurchaseOrder;
import com.inventory.inventory_management_system.purchase.entity.PurchaseOrderItem;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "poNumber", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "status", ignore = true)
    PurchaseOrder toEntity(CreatePurchaseOrderRequest request);

    PurchaseOrderResponse toResponse(PurchaseOrder entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "purchaseOrder", ignore = true)
    @Mapping(target = "orderedQuantity", source = "quantity")
    @Mapping(target = "receivedQuantity", constant = "0")
    @Mapping(target = "totalPrice", ignore = true)
    PurchaseOrderItem toItemEntity(CreatePurchaseOrderRequest.PurchaseOrderItemRequest itemRequest);

    PurchaseOrderItemResponse toItemResponse(PurchaseOrderItem item);
}
