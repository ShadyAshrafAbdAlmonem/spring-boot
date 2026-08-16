package com.inventory.inventory_management_system.role.service.impl;

import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.permission.entity.Permission;
import com.inventory.inventory_management_system.permission.repository.PermissionRepository;
import com.inventory.inventory_management_system.role.dto.request.CreateRoleRequest;
import com.inventory.inventory_management_system.role.dto.request.UpdateRoleRequest;
import com.inventory.inventory_management_system.role.dto.response.RoleDetailsResponse;
import com.inventory.inventory_management_system.role.dto.response.RoleResponse;
import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.role.mapper.RoleMapper;
import com.inventory.inventory_management_system.role.repository.RoleRepository;
import com.inventory.inventory_management_system.role.service.RoleService;
import com.inventory.inventory_management_system.role.validator.RoleValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleMapper roleMapper;
    private final RoleValidator roleValidator;

    @Override
    @Transactional
    public RoleDetailsResponse createRole(CreateRoleRequest request) {
        roleValidator.validateCreate(request);

        List<Permission> permissions = fetchAndValidatePermissions(request.getPermissionIds());

        Role role = roleMapper.toEntity(request);
        role.setPermissions(new HashSet<>(permissions));

        Role savedRole = roleRepository.save(role);
        return roleMapper.toDetailsResponse(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleMapper.toResponseList(roleRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public RoleDetailsResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return roleMapper.toDetailsResponse(role);
    }

    @Override
    @Transactional
    public RoleDetailsResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        roleValidator.validateUpdate(role, request);

        List<Permission> permissions = fetchAndValidatePermissions(request.getPermissionIds());

        role.setName(request.getName());
        role.setPermissions(new HashSet<>(permissions));

        Role updatedRole = roleRepository.save(role);
        return roleMapper.toDetailsResponse(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
    }

    private List<Permission> fetchAndValidatePermissions(java.util.Set<Long> permissionIds) {
        List<Permission> permissions = permissionRepository.findAllById(permissionIds);
        if (permissions.size() != permissionIds.size()) {
            throw new ResourceNotFoundException("One or more provided permission IDs were not found.");
        }
        return permissions;
    }
}