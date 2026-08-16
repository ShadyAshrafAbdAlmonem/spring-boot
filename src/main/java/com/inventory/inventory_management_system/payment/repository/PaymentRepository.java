package com.inventory.inventory_management_system.payment.repository;

import com.inventory.inventory_management_system.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    Optional<Payment> findByTransactionId(String transactionId);
    List<Payment> findByInvoiceId(Long invoiceId);
    Page<Payment> findByCustomerId(Long customerId, Pageable pageable);
}
