package com.inventory.inventory_management_system.permission.mapper;

import com.inventory.inventory_management_system.permission.dto.response.PermissionResponse;
import com.inventory.inventory_management_system.permission.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionResponse toResponse(Permission permission);
}