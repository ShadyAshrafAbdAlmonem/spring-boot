package com.inventory.inventory_management_system.role.validator;

import com.inventory.inventory_management_system.common.exception.DuplicateResourceException;
import com.inventory.inventory_management_system.role.dto.request.CreateRoleRequest;
import com.inventory.inventory_management_system.role.dto.request.UpdateRoleRequest;
import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {

    private final RoleRepository roleRepository;

    public void validateCreate(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role already exists with name: " + request.getName());
        }
    }

    public void validateUpdate(Role existingRole, UpdateRoleRequest request) {
        if (!existingRole.getName().equalsIgnoreCase(request.getName()) 
                && roleRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Role name already taken: " + request.getName());
        }
    }
}