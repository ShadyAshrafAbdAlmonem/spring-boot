package com.inventory.inventory_management_system.customer.service.impl;

import com.inventory.inventory_management_system.customer.dto.request.CreateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.request.UpdateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.response.CustomerResponse;
import com.inventory.inventory_management_system.customer.entity.Customer;
import com.inventory.inventory_management_system.customer.entity.CustomerAddress;
import com.inventory.inventory_management_system.customer.mapper.CustomerMapper;
import com.inventory.inventory_management_system.customer.repository.CustomerRepository;
import com.inventory.inventory_management_system.customer.service.CustomerService;
import com.inventory.inventory_management_system.customer.specification.CustomerSpecification;
import com.inventory.inventory_management_system.customer.validator.CustomerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidator customerValidator;

    @Override
    public CustomerResponse createCustomer(@NonNull CreateCustomerRequest request) {
        customerValidator.validateCreate(request);

        Customer customer = customerMapper.toEntity(request);
        customer.setActive(true);

        if (request.getAddresses() != null) {
            for (CreateCustomerRequest.AddressDto dto : request.getAddresses()) {
                CustomerAddress address = customerMapper.addressDtoToEntity(dto);
                customer.addAddress(address);
            }
        }

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(@NonNull Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomers(String name, String email, Boolean active, @NonNull Pageable pageable) {
        Specification<Customer> spec = Specification.where(CustomerSpecification.hasNameLike(name))
                .and(CustomerSpecification.hasEmail(email))
                .and(CustomerSpecification.isActive(active));

        return customerRepository.findAll(spec, pageable).map(customerMapper::toResponse);
    }

    @Override
    public CustomerResponse updateCustomer(@NonNull Long id, @NonNull UpdateCustomerRequest request) {
        customerValidator.validateUpdate(id, request);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customerMapper.updateEntityFromDto(request, customer);
        return customerMapper.toResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteCustomer(@NonNull Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
}
