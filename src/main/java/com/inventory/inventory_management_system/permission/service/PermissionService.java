package com.inventory.inventory_management_system.permission.service;

import com.inventory.inventory_management_system.permission.entity.Permission;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PermissionService {
    Permission createPermission(@NonNull Permission permission);
    List<Permission> getAllPermissions();
    Permission getPermissionById(@NonNull Long id);
    void deletePermission(@NonNull Long id);
}
