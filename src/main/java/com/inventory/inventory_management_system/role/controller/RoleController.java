package com.inventory.inventory_management_system.role.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.role.dto.request.CreateRoleRequest;
import com.inventory.inventory_management_system.role.dto.request.UpdateRoleRequest;
import com.inventory.inventory_management_system.role.dto.response.RoleDetailsResponse;
import com.inventory.inventory_management_system.role.dto.response.RoleResponse;
import com.inventory.inventory_management_system.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<RoleDetailsResponse>> createRole(@Valid @RequestBody @NonNull CreateRoleRequest request) {
        RoleDetailsResponse created = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Role created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
        return ResponseEntity.ok(ApiResponse.success(roleService.getAllRoles()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDetailsResponse>> getRoleById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(ApiResponse.success(roleService.getRoleById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleDetailsResponse>> updateRole(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateRoleRequest request) {
        RoleDetailsResponse updated = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Role updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable @NonNull Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Role deleted successfully"));
    }
}