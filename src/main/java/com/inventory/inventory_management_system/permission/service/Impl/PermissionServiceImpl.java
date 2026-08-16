package com.inventory.inventory_management_system.permission.service.Impl;

import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.permission.entity.Permission;
import com.inventory.inventory_management_system.permission.repository.PermissionRepository;
import com.inventory.inventory_management_system.permission.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public Permission createPermission(@NonNull Permission permission) {
        if (permissionRepository.existsByName(permission.getName())) {
            throw new DuplicateResourceException("Permission already exists with name: " + permission.getName());
        }
        return permissionRepository.save(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Permission getPermissionById(@NonNull Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    @Transactional
    public void deletePermission(@NonNull Long id) {
        if (!permissionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Permission not found with id: " + id);
        }
        permissionRepository.deleteById(id);
    }
}
