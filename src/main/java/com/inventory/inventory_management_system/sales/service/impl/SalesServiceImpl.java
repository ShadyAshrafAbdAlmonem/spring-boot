package com.inventory.inventory_management_system.sales.service.impl;

import com.inventory.inventory_management_system.sales.dto.request.CancelSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CompleteSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.request.CreateSalesOrderRequest;
import com.inventory.inventory_management_system.sales.dto.response.SalesOrderResponse;
import com.inventory.inventory_management_system.sales.entity.SalesOrder;
import com.inventory.inventory_management_system.sales.entity.SalesOrderItem;
import com.inventory.inventory_management_system.sales.mapper.SalesMapper;
import com.inventory.inventory_management_system.sales.repository.SalesOrderRepository;
import com.inventory.inventory_management_system.sales.service.SalesService;
import com.inventory.inventory_management_system.sales.specification.SalesSpecification;
import com.inventory.inventory_management_system.sales.validator.SalesValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class SalesServiceImpl implements SalesService {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesMapper salesMapper;
    private final SalesValidator salesValidator;

    @Override
    public SalesOrderResponse createSalesOrder(@NonNull CreateSalesOrderRequest request) {
        salesValidator.validateCreate(request);

        SalesOrder order = salesMapper.toEntity(request);
        order.setOrderNumber("SO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;
        for (CreateSalesOrderRequest.SalesOrderItemRequest itemDto : request.getItems()) {
            SalesOrderItem item = salesMapper.toItemEntity(itemDto);
            BigDecimal itemTotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            item.setTotalPrice(itemTotal);
            item.setSalesOrder(order);
            order.getItems().add(item);
            total = total.add(itemTotal);
        }

        order.setTotalAmount(total);
        return salesMapper.toResponse(salesOrderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public SalesOrderResponse getSalesOrderById(Long id) {
        SalesOrder order = salesOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales Order not found with id: " + id));
        return salesMapper.toResponse(order);
    }

    @Override
    public SalesOrderResponse completeSalesOrder(CompleteSalesOrderRequest request) {
        SalesOrder order = salesOrderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Sales Order not found with id: " + request.getOrderId()));

        order.setStatus("COMPLETED");
        return salesMapper.toResponse(salesOrderRepository.save(order));
    }

    @Override
    public SalesOrderResponse cancelSalesOrder(@NonNull CancelSalesOrderRequest request) {
        SalesOrder order = salesOrderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Sales Order not found with id: " + request.getOrderId()));

        salesValidator.validateCancelable(order);
        order.setStatus("CANCELLED");
        order.setNotes("Cancelled: " + request.getReason());

        return salesMapper.toResponse(salesOrderRepository.save(order));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SalesOrderResponse> filterSalesOrders(Long customerId, String status, Pageable pageable) {
        Specification<SalesOrder> spec = Specification.where(SalesSpecification.hasCustomerId(customerId))
                .and(SalesSpecification.hasStatus(status));
        return salesOrderRepository.findAll(spec, pageable).map(salesMapper::toResponse);
    }
}
