package com.inventory.inventory_management_system.activitylog.mapper;

import com.inventory.inventory_management_system.activitylog.dto.response.ActivityLogResponse;
import com.inventory.inventory_management_system.activitylog.entity.ActivityLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActivityLogMapper {
    ActivityLogResponse toResponse(ActivityLog entity);
}