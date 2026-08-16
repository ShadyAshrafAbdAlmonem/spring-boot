package com.inventory.inventory_management_system.shipment.mapper;

import com.inventory.inventory_management_system.shipment.dto.request.ShipmentRequest;
import com.inventory.inventory_management_system.shipment.dto.response.ShipmentResponse;
import com.inventory.inventory_management_system.shipment.dto.response.ShipmentTrackingResponse;
import com.inventory.inventory_management_system.shipment.entity.Shipment;
import com.inventory.inventory_management_system.shipment.entity.ShipmentTracking;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShipmentMapper {

    Shipment toEntity(ShipmentRequest request);

    ShipmentResponse toResponse(Shipment entity);

    ShipmentTrackingResponse toTrackingResponse(ShipmentTracking tracking);
}
