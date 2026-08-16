package com.inventory.inventory_management_system.user.dto.request;

import com.inventory.inventory_management_system.common.enums.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserRequest {

    @Size(min = 3, max = 50)
    private String username;

    @Email
    private String email;

    private UserStatus status;

    private Set<Long> roleIds;
}
