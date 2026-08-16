package com.inventory.inventory_management_system.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailsResponse {
    private Long id;
    private String name;
    private Set<PermissionResponse> permissions;
}