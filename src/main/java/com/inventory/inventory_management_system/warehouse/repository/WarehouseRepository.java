package com.inventory.inventory_management_system.warehouse.repository;

import com.inventory.inventory_management_system.warehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {
    boolean existsByName(String name);
    boolean existsByCode(String code);
    Optional<Warehouse> findByCode(String code);
}
