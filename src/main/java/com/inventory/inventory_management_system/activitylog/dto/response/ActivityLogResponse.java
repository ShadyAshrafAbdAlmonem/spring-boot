package com.inventory.inventory_management_system.activitylog.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityLogResponse {
    private Long id;
    private String username;
    private String action;
    private String entityName;
    private Long entityId;
    private String description;
    private String ipAddress;
    private LocalDateTime timestamp;
}
