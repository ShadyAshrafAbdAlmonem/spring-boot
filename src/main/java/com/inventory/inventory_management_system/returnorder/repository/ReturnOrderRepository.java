package com.inventory.inventory_management_system.returnorder.repository;

import com.inventory.inventory_management_system.returnorder.entity.ReturnOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReturnOrderRepository extends JpaRepository<ReturnOrder, Long>, JpaSpecificationExecutor<ReturnOrder> {
    Optional<ReturnOrder> findByReturnNumber(String returnNumber);
    Page<ReturnOrder> findByCustomerId(Long customerId, Pageable pageable);
}
