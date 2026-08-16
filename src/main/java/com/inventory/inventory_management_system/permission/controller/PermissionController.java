package com.inventory.inventory_management_system.permission.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.permission.entity.Permission;
import com.inventory.inventory_management_system.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/permissions")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping
    public ResponseEntity<ApiResponse<Permission>> createPermission(@RequestBody Permission permission) {
        Permission created = permissionService.createPermission(permission);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Permission created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Permission>>> getAllPermissions() {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getAllPermissions()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Permission>> getPermissionById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getPermissionById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable @NonNull Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Permission deleted successfully"));
    }
}