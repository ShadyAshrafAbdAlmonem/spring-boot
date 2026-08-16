package com.inventory.inventory_management_system.customer.service;

import com.inventory.inventory_management_system.customer.dto.request.CreateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.request.UpdateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

public interface CustomerService {
    CustomerResponse createCustomer(@NonNull CreateCustomerRequest request);
    CustomerResponse getCustomerById(@NonNull Long id);
    Page<CustomerResponse> getCustomers(String name, String email, Boolean active, @NonNull Pageable pageable);
    CustomerResponse updateCustomer(@NonNull Long id, @NonNull UpdateCustomerRequest request);
    void deleteCustomer(@NonNull Long id);
}
