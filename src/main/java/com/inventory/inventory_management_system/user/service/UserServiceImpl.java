package com.inventory.inventory_management_system.user.service;

import com.inventory.inventory_management_system.common.enums.UserStatus;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.role.entity.Role;
import com.inventory.inventory_management_system.role.repository.RoleRepository;
import com.inventory.inventory_management_system.user.dto.request.CreateUserRequest;
import com.inventory.inventory_management_system.user.dto.request.UpdateUserRequest;
import com.inventory.inventory_management_system.user.dto.response.UserResponse;
import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import com.inventory.inventory_management_system.user.entity.User; // This import is duplicated and should be removed if User is in the same package
import com.inventory.inventory_management_system.user.mapper.UserMapper;
import com.inventory.inventory_management_system.user.repository.UserRepository;
import com.inventory.inventory_management_system.user.specification.UserSpecification;
import com.inventory.inventory_management_system.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Override
    @Transactional
    public UserResponse createUser(@NonNull CreateUserRequest request) { // @NonNull added to interface
        // 1. Validate business constraints (uniqueness)
        userValidator.validateCreateUser(request);

        // 2. Fetch roles by IDs
        List<Role> roles = roleRepository.findAllById(request.getRoleIds() != null ? request.getRoleIds() : Collections.emptySet());
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty() && roles.isEmpty()) {
            throw new ResourceNotFoundException("No valid roles found for the provided role IDs: " + request.getRoleIds());
        }

        // 3. Map to Entity & Save (Note: Remember to encode password if Spring Security BCryptPasswordEncoder is integrated)
        User user = userMapper.toEntity(request, new HashSet<>(roles));
        User savedUser = userRepository.save(user);

        // 4. Return DTO Response
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(@NonNull Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAllUsers(int page, int size, String username, String email, UserStatus status) {
        Pageable pageable = PageRequest.of(page, size);

        // Build dynamic specification query
        Specification<User> spec = Specification.where(UserSpecification.hasUsername(username))
                .and(UserSpecification.hasEmail(email))
                .and(UserSpecification.hasStatus(status));

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserResponse> content = userPage.getContent().stream()
                .map(userMapper::toResponse)
                .toList();

        return PageResponse.<UserResponse>builder()
                .content(content)
                .pageNumber(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUser(@NonNull Long id, @NonNull UpdateUserRequest request) { // @NonNull added to interface
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            user.setEmail(request.getEmail());
        }

        if (request.getStatus() != null) {
            user.setStatus(request.getStatus());
        }

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            List<Role> roles = roleRepository.findAllById(request.getRoleIds()); // Assuming User entity has setRoles or is handled by Lombok @Setter
            user.setRoles(new HashSet<>(roles));
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(@NonNull Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}