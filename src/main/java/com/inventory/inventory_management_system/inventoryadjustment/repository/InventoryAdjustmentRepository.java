package com.inventory.inventory_management_system.inventoryadjustment.repository;

import com.inventory.inventory_management_system.inventoryadjustment.entity.InventoryAdjustment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryAdjustmentRepository extends JpaRepository<InventoryAdjustment, Long> {
    Page<InventoryAdjustment> findByWarehouseId(Long warehouseId, Pageable pageable);
    Page<InventoryAdjustment> findByProductId(Long productId, Pageable pageable);
}
