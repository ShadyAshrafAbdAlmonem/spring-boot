package com.inventory.inventory_management_system.activitylog.entity;

import com.inventory.inventory_management_system.common.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String username;
    private String action;      // e.g., "CREATE_PRODUCT", "LOGIN"

    @Column(name = "entity_type")
    private String entityName;  // e.g., "Product", "User"

    private Long entityId;

    @Column(length = 1000)
    private String description;

    private String ipAddress;

    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
