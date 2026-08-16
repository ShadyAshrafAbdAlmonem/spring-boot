package com.inventory.inventory_management_system.stock.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockMovement extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Long sourceWarehouseId;
    private Long targetWarehouseId;
    private Integer quantity;
    
    private String movementType; // e.g., ADD, REMOVE, TRANSFER
    private String reason;
    private LocalDateTime timestamp;
}
