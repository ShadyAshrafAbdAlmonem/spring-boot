package com.inventory.inventory_management_system.activitylog.service;

import com.inventory.inventory_management_system.activitylog.dto.request.ActivityLogFilterRequest;
import com.inventory.inventory_management_system.activitylog.dto.response.ActivityLogResponse;
import com.inventory.inventory_management_system.activitylog.entity.ActivityLog;
import com.inventory.inventory_management_system.common.response.PageResponse;
import org.springframework.lang.NonNull;

public interface ActivityLogService {
    void logActivity(@NonNull ActivityLog activityLog);
    @NonNull PageResponse<ActivityLogResponse> getLogs(@NonNull ActivityLogFilterRequest filter, int page, int size);
}