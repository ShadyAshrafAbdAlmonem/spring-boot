package com.inventory.inventory_management_system.sales.service;

import com.inventory.inventory_management_system.sales.dto.request.CancelSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CompleteSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CreateSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.response.SalesOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
 
public interface SalesService {
    SalesOrderResponse createSalesOrder(@NonNull CreateSalesOrderRequest request);
    SalesOrderResponse getSalesOrderById(@NonNull Long id);
    SalesOrderResponse completeSalesOrder(@NonNull CompleteSalesOrderRequest request);
    SalesOrderResponse cancelSalesOrder(@NonNull CancelSalesOrderRequest request);
    Page<SalesOrderResponse> filterSalesOrders(Long customerId, String status, @NonNull Pageable pageable);
}
