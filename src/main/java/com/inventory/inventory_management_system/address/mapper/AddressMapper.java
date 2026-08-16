package com.inventory.inventory_management_system.address.mapper;

import com.inventory.inventory_management_system.address.dto.request.CreateAddressRequest;
import com.inventory.inventory_management_system.address.dto.request.UpdateAddressRequest;
import com.inventory.inventory_management_system.address.dto.response.AddressResponse;
import com.inventory.inventory_management_system.address.entity.Address;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    @NonNull Address toEntity(@NonNull CreateAddressRequest request);

    @NonNull AddressResponse toResponse(@NonNull Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(@NonNull UpdateAddressRequest request, @MappingTarget @NonNull Address entity);
}
