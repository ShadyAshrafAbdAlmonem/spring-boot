package com.inventory.inventory_management_system.purchase.service;

import com.inventory.inventory_management_system.purchase.dto.request.CreatePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.ReceivePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.UpdatePurchaseStatusRequest;
import com.inventory.inventory_management_system.purchase.dto.response.PurchaseOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
 
public interface PurchaseService {
    PurchaseOrderResponse createPurchaseOrder(@NonNull CreatePurchaseOrderRequest request);
    PurchaseOrderResponse getPurchaseOrderById(@NonNull Long id);
    Page<PurchaseOrderResponse> getPurchaseOrders(Long supplierId, String status, @NonNull Pageable pageable);
    PurchaseOrderResponse receiveItems(@NonNull ReceivePurchaseOrderRequest request);
    PurchaseOrderResponse updateStatus(@NonNull Long id, UpdatePurchaseStatusRequest request);
}
