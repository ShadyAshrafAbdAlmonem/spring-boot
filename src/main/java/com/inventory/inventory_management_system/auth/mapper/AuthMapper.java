package com.inventory.inventory_management_system.auth.mapper;

import com.inventory.inventory_management_system.auth.dto.request.RegisterRequest;
import com.inventory.inventory_management_system.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @NonNull User toUserEntity(@NonNull RegisterRequest request);
}
