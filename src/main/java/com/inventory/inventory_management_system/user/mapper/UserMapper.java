package com.inventory.inventory_management_system.user.mapper;

import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.user.dto.request.CreateUserRequest;
import com.inventory.inventory_management_system.user.dto.response.UserResponse;
import com.inventory.inventory_management_system.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@SuppressWarnings("null")
public class UserMapper {

    public User toEntity(CreateUserRequest request, Set<Role> roles) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // Password encoding should be applied in Service
                .status(request.getStatus())
                .roles(roles)
                .build();
    }

    public UserResponse toResponse(User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(roleNames)
                .build();
    }
}