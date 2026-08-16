package com.inventory.inventory_management_system.user.service;

import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.user.dto.request.CreateUserRequest;
import com.inventory.inventory_management_system.user.dto.request.UpdateUserRequest;
import com.inventory.inventory_management_system.user.dto.response.UserResponse;
import org.springframework.lang.NonNull;

public interface UserService {
    UserResponse createUser(@NonNull CreateUserRequest request);
    UserResponse getUserById(@NonNull Long id);
    PageResponse<UserResponse> getAllUsers(int page, int size, String username, String email, UserStatus status);
    UserResponse updateUser(@NonNull Long id, @NonNull UpdateUserRequest request);
    void deleteUser(@NonNull Long id);
}
