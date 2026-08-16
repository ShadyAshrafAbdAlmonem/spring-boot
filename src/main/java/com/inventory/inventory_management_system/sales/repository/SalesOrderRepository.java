package com.inventory.inventory_management_system.sales.repository;

import com.inventory.inventory_management_system.sales.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long>, JpaSpecificationExecutor<SalesOrder> {
    Optional<SalesOrder> findByOrderNumber(String orderNumber);
    Page<SalesOrder> findByCustomerId(Long customerId, Pageable pageable);
}
