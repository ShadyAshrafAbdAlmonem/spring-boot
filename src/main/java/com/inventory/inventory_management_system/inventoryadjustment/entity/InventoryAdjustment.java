package com.inventory.inventory_management_system.inventoryadjustment.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_adjustments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryAdjustment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer previousQuantity;

    @Column(nullable = false)
    private Integer adjustedQuantity;

    @Column(nullable = false)
    private Integer newQuantity;

    @Column(nullable = false)
    private String reason; // e.g., DAMAGE, CORRECTION, THEFT, EXPIRED

    private String note;

    @Column(nullable = false)
    private String adjustedBy;

    @Column(nullable = false)
    private LocalDateTime adjustedAt;
}
