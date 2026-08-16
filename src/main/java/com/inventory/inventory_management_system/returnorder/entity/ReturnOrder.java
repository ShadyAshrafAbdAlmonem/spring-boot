package com.inventory.inventory_management_system.returnorder.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "return_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnOrder extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String returnNumber;

    @Column(nullable = false)
    private Long salesOrderId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String status; // REQUESTED, APPROVED, REJECTED, COMPLETED

}
