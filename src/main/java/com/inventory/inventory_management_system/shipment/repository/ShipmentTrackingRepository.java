package com.inventory.inventory_management_system.shipment.repository;

import com.inventory.inventory_management_system.shipment.entity.ShipmentTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface ShipmentTrackingRepository extends JpaRepository<ShipmentTracking, Long> {

    // 1. جلب كل سجلات التتبع لشحنة معينة مرتبة من الأحدث للأقدم
    List<ShipmentTracking> findByShipmentIdOrderByTimestampDesc(Long shipmentId);

    // 2. جلب آخر تحديث حالة لشحنة معينة
    Optional<ShipmentTracking> findFirstByShipmentIdOrderByTimestampDesc(Long shipmentId);

    // 3. مسح جميع سجلات التتبع لشحنة معينة (إذا لزم الأمر)
    void deleteByShipmentId(Long shipmentId);
}