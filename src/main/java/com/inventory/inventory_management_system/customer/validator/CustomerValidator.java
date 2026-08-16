package com.inventory.inventory_management_system.customer.validator;

import com.inventory.inventory_management_system.customer.dto.request.CreateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.request.UpdateCustomerRequest;
import com.inventory.inventory_management_system.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerValidator {

    private final CustomerRepository customerRepository;

    public void validateCreate(CreateCustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + request.getEmail());
        }
        if (customerRepository.existsByPhone(request.getPhone())) {
            throw new IllegalArgumentException("Phone number already in use: " + request.getPhone());
        }
    }

    public void validateUpdate(Long customerId, UpdateCustomerRequest request) {
        if (request.getEmail() != null) {
            customerRepository.findByEmail(request.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(customerId)) {
                    throw new IllegalArgumentException("Email already in use by another customer");
                }
            });
        }
    }
}
