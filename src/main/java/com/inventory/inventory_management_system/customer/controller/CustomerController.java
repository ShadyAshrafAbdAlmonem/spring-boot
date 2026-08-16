package com.inventory.inventory_management_system.customer.controller;

import com.inventory.inventory_management_system.customer.dto.request.CreateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.request.UpdateCustomerRequest;
import com.inventory.inventory_management_system.customer.dto.response.CustomerResponse;
import com.inventory.inventory_management_system.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody @NonNull CreateCustomerRequest request) {
        return new ResponseEntity<>(customerService.createCustomer(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomers(name, email, active, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable @NonNull Long id, @Valid @RequestBody @NonNull UpdateCustomerRequest request) {
        return ResponseEntity.ok(customerService.updateCustomer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
