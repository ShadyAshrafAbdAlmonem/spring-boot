package com.inventory.inventory_management_system.purchase.service.impl;

import com.inventory.inventory_management_system.purchase.dto.request.CreatePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.ReceivePurchaseOrderRequest;
import com.inventory.inventory_management_system.purchase.dto.request.UpdatePurchaseStatusRequest;
import com.inventory.inventory_management_system.purchase.dto.response.PurchaseOrderResponse;
import com.inventory.inventory_management_system.purchase.entity.PurchaseOrder;
import com.inventory.inventory_management_system.purchase.entity.PurchaseOrderItem;
import com.inventory.inventory_management_system.purchase.mapper.PurchaseMapper;
import com.inventory.inventory_management_system.purchase.repository.PurchaseOrderRepository;
import com.inventory.inventory_management_system.purchase.service.PurchaseService;
import com.inventory.inventory_management_system.purchase.specification.PurchaseSpecification;
import com.inventory.inventory_management_system.purchase.validator.PurchaseValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseValidator purchaseValidator;

    @Override
    public PurchaseOrderResponse createPurchaseOrder(@NonNull CreatePurchaseOrderRequest request) {
        purchaseValidator.validateCreate(request);

        PurchaseOrder po = purchaseMapper.toEntity(request);
        po.setPoNumber("PO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        po.setStatus("PENDING");
        po.setCreatedAt(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreatePurchaseOrderRequest.PurchaseOrderItemRequest itemDto : request.getItems()) {
            PurchaseOrderItem item = purchaseMapper.toItemEntity(itemDto);
            BigDecimal itemTotal = itemDto.getUnitPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            item.setTotalPrice(itemTotal);
            item.setPurchaseOrder(po);
            po.getItems().add(item);
            totalAmount = totalAmount.add(itemTotal);
        }

        po.setTotalAmount(totalAmount);
        return purchaseMapper.toResponse(purchaseOrderRepository.save(po));
    }

    @Override
    @Transactional(readOnly = true)
    public PurchaseOrderResponse getPurchaseOrderById(@NonNull Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + id));
        return purchaseMapper.toResponse(po);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseOrderResponse> getPurchaseOrders(Long supplierId, String status, @NonNull Pageable pageable) {
        Specification<PurchaseOrder> spec = Specification.where(PurchaseSpecification.hasSupplier(supplierId))
                .and(PurchaseSpecification.hasStatus(status));
        return purchaseOrderRepository.findAll(spec, pageable).map(purchaseMapper::toResponse);
    }

    @Override
    public PurchaseOrderResponse receiveItems(ReceivePurchaseOrderRequest request) {
        PurchaseOrder po = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + request.getPurchaseOrderId()));

        Map<Long, Integer> receivedMap = request.getItems().stream()
                .collect(Collectors.toMap(ReceivePurchaseOrderRequest.ReceivedItemRequest::getItemId, ReceivePurchaseOrderRequest.ReceivedItemRequest::getQuantityReceived));

        boolean allReceived = true;
        boolean anyReceived = false;

        for (PurchaseOrderItem item : po.getItems()) {
            if (receivedMap.containsKey(item.getId())) {
                int newReceived = item.getReceivedQuantity() + receivedMap.get(item.getId());
                item.setReceivedQuantity(newReceived);
            }

            if (item.getReceivedQuantity() < item.getOrderedQuantity()) {
                allReceived = false;
            }
            if (item.getReceivedQuantity() > 0) {
                anyReceived = true;
            }
        }

        if (allReceived) {
            po.setStatus("RECEIVED");
        } else if (anyReceived) {
            po.setStatus("PARTIALLY_RECEIVED");
        }

        return purchaseMapper.toResponse(purchaseOrderRepository.save(po));
    }

    @Override
    public PurchaseOrderResponse updateStatus(@NonNull Long id, UpdatePurchaseStatusRequest request) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with id: " + id));

        purchaseValidator.validateStatusTransition(po, request.getStatus());
        po.setStatus(request.getStatus());

        return purchaseMapper.toResponse(purchaseOrderRepository.save(po));
    }
}