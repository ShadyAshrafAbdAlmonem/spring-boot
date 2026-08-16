package com.inventory.inventory_management_system.shipment.controller;

import com.inventory.inventory_management_system.shipment.dto.request.ShipmentRequest;
import com.inventory.inventory_management_system.shipment.dto.request.UpdateShipmentStatusRequest;
import com.inventory.inventory_management_system.shipment.dto.response.ShipmentResponse;
import com.inventory.inventory_management_system.shipment.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/shipments")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping
    public ResponseEntity<ShipmentResponse> create(@Valid @RequestBody @NonNull ShipmentRequest request) {
        return new ResponseEntity<>(shipmentService.createShipment(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @GetMapping("/tracking/{trackingNumber}")
    public ResponseEntity<ShipmentResponse> getByTrackingNumber(@PathVariable String trackingNumber) {
        return ResponseEntity.ok(shipmentService.getShipmentByTrackingNumber(trackingNumber));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ShipmentResponse> updateStatus(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateShipmentStatusRequest request) {
        return ResponseEntity.ok(shipmentService.updateStatus(id, request));
    }
}
