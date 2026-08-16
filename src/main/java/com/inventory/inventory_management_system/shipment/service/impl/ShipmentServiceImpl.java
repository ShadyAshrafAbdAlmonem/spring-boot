package com.inventory.inventory_management_system.shipment.service.impl;

import com.inventory.inventory_management_system.shipment.dto.request.ShipmentRequest;
import com.inventory.inventory_management_system.shipment.dto.request.UpdateShipmentStatusRequest;
import com.inventory.inventory_management_system.shipment.dto.response.ShipmentResponse;
import com.inventory.inventory_management_system.shipment.entity.Shipment;
import com.inventory.inventory_management_system.shipment.entity.ShipmentTracking;
import com.inventory.inventory_management_system.shipment.mapper.ShipmentMapper;
import com.inventory.inventory_management_system.shipment.repository.ShipmentRepository;
import com.inventory.inventory_management_system.shipment.service.ShipmentService;
import com.inventory.inventory_management_system.shipment.validator.ShipmentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentValidator shipmentValidator;

    @Override
    public ShipmentResponse createShipment(@NonNull ShipmentRequest request) {
        shipmentValidator.validateCreate(request);

        Shipment shipment = shipmentMapper.toEntity(request);
        shipment.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        shipment.setStatus("PENDING");

        ShipmentTracking initialTracking = ShipmentTracking.builder()
                .status("PENDING")
                .location("Warehouse")
                .remarks("Shipment created")
                .timestamp(LocalDateTime.now())
                .shipment(shipment)
                .build();

        shipment.getTrackingLogs().add(initialTracking);
        return shipmentMapper.toResponse(shipmentRepository.save(shipment));
    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponse getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));
        return shipmentMapper.toResponse(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponse getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Shipment not found with tracking number: " + trackingNumber));
        return shipmentMapper.toResponse(shipment);
    }

    @Override
    public ShipmentResponse updateStatus(Long id, UpdateShipmentStatusRequest request) {
        shipmentValidator.validateStatusUpdate(request);

        Shipment shipment = shipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + id));

        shipment.setStatus(request.getStatus());

        ShipmentTracking tracking = ShipmentTracking.builder()
                .status(request.getStatus())
                .location(request.getLocation())
                .remarks(request.getRemarks())
                .timestamp(LocalDateTime.now())
                .shipment(shipment)
                .build();

        shipment.getTrackingLogs().add(tracking);
        return shipmentMapper.toResponse(shipmentRepository.save(shipment));
    }
}
