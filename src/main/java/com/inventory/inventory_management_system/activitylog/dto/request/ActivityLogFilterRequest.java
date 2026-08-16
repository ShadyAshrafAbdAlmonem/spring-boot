package com.inventory.inventory_management_system.activitylog.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityLogFilterRequest {
    private String username;
    private String action;
    private String entityName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
