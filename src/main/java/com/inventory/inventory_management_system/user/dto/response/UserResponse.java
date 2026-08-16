package com.inventory.inventory_management_system.user.dto.response;

import com.inventory.inventory_management_system.common.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserStatus status;
    private Set<String> roles; // Send role names instead of whole Entity
}
