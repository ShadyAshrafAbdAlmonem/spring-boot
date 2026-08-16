package com.inventory.inventory_management_system.inventorycount.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_counts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryCount extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long warehouseId;

    @Column(nullable = false)
    private String countReference;

    @Column(nullable = false)
    private String status; // PLANNED, IN_PROGRESS, COMPLETED, CANCELLED

    private String conductedBy;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

    private String notes;
}