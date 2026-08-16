package com.inventory.inventory_management_system.customer.mapper;

import com.inventory.inventory_management_system.customer.dto.request.CreateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.request.UpdateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.response.CustomerResponse;
import com.inventory.inventory_management_system.customer.entity.Customer;
import com.inventory.inventory_management_system.customer.entity.CustomerAddress;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    @Mapping(target = "addresses", ignore = true)
    @NonNull Customer toEntity(@NonNull CreateCustomerRequest request);

    @NonNull CustomerAddress addressDtoToEntity(@NonNull CreateCustomerRequest.AddressDto dto);

    @NonNull CustomerResponse toResponse(@NonNull Customer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(@NonNull UpdateCustomerRequest request, @MappingTarget @NonNull Customer entity);
}