package com.inventory.inventory_management_system.role.service;

import com.inventory.inventory_management_system.role.dto.request.CreateRoleRequest;
import com.inventory.inventory_management_system.role.dto.request.UpdateRoleRequest;
import com.inventory.inventory_management_system.role.dto.response.RoleDetailsResponse;
import com.inventory.inventory_management_system.role.dto.response.RoleResponse;
import org.springframework.lang.NonNull;

import java.util.List;

public interface RoleService {
    RoleDetailsResponse createRole(@NonNull CreateRoleRequest request);
    List<RoleResponse> getAllRoles();
    RoleDetailsResponse getRoleById(@NonNull Long id);
    RoleDetailsResponse updateRole(@NonNull Long id, @NonNull UpdateRoleRequest request);
    void deleteRole(@NonNull Long id);
}
