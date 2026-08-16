package com.inventory.inventory_management_system.returnorder.service.impl;

import com.inventory.inventory_management_system.returnorder.dto.request.CreateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.ReturnOrderFilterRequest;
import com.inventory.inventory_management_system.returnorder.dto.request.UpdateReturnOrderRequest;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderDetailsResponse;
import com.inventory.inventory_management_system.returnorder.dto.response.ReturnOrderResponse;
import com.inventory.inventory_management_system.returnorder.entity.ReturnOrder;
import com.inventory.inventory_management_system.returnorder.mapper.ReturnOrderMapper;
import com.inventory.inventory_management_system.returnorder.repository.ReturnOrderRepository;
import com.inventory.inventory_management_system.returnorder.service.ReturnOrderService;
import com.inventory.inventory_management_system.returnorder.validator.ReturnOrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class ReturnOrderServiceImpl implements ReturnOrderService {

    private final ReturnOrderRepository returnOrderRepository;
    private final ReturnOrderMapper returnOrderMapper;
    private final ReturnOrderValidator returnOrderValidator;

    @Override
    public ReturnOrderDetailsResponse createReturnOrder(CreateReturnOrderRequest request) {
        returnOrderValidator.validateCreate(request);

        ReturnOrder returnOrder = returnOrderMapper.toEntity(request);
        returnOrder.setReturnNumber("RMA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        returnOrder.setStatus("REQUESTED");
        returnOrder.setCreatedAt(LocalDateTime.now());

        return returnOrderMapper.toDetailsResponse(returnOrderRepository.save(returnOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public ReturnOrderDetailsResponse getReturnOrderById(Long id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Return Order not found with id: " + id));
        return returnOrderMapper.toDetailsResponse(returnOrder);
    }

    @Override
    public ReturnOrderDetailsResponse updateReturnOrderStatus(Long id, UpdateReturnOrderRequest request) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Return Order not found with id: " + id));

        returnOrderValidator.validateUpdate(returnOrder, request.getStatus());
        returnOrder.setStatus(request.getStatus());

        return returnOrderMapper.toDetailsResponse(returnOrderRepository.save(returnOrder));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReturnOrderResponse> filterReturnOrders(ReturnOrderFilterRequest filter, Pageable pageable) {
        if (filter.getCustomerId() != null) {
            return returnOrderRepository.findByCustomerId(filter.getCustomerId(), pageable)
                    .map(returnOrderMapper::toResponse);
        }
        return returnOrderRepository.findAll(pageable).map(returnOrderMapper::toResponse);
    }
}
