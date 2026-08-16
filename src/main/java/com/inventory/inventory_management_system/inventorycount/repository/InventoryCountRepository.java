package com.inventory.inventory_management_system.inventorycount.repository;

import com.inventory.inventory_management_system.inventorycount.entity.InventoryCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryCountRepository extends JpaRepository<InventoryCount, Long> {
    Page<InventoryCount> findByWarehouseId(Long warehouseId, Pageable pageable);
    Optional<InventoryCount> findByCountReference(String countReference);
    boolean existsByCountReference(String countReference);
}
