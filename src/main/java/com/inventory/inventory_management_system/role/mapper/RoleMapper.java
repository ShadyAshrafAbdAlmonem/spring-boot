package com.inventory.inventory_management_system.role.mapper;

import com.inventory.inventory_management_system.role.dto.request.CreateRoleRequest;
import com.inventory.inventory_management_system.role.dto.response.RoleDetailsResponse;
import com.inventory.inventory_management_system.role.dto.response.RoleResponse;
import com.inventory.inventory_management_system.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    Role toEntity(CreateRoleRequest request);

    RoleResponse toResponse(Role role);

    RoleDetailsResponse toDetailsResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> roles);
}