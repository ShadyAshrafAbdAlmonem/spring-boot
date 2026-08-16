package com.inventory.inventory_management_system.returnorder.service;

import com.inventory.inventory_management_system.returnorder.dto.request.CreateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.ReturnOrderFilterRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.UpdateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderDetailsResponse;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface ReturnOrderService {
    ReturnOrderDetailsResponse createReturnOrder(@NonNull CreateReturnOrderRequest request);
    ReturnOrderDetailsResponse getReturnOrderById(@NonNull Long id);
    ReturnOrderDetailsResponse updateReturnOrderStatus(@NonNull Long id, @NonNull UpdateReturnOrderRequest request);
    Page<ReturnOrderResponse> filterReturnOrders(ReturnOrderFilterRequest filter, @NonNull Pageable pageable);
}
