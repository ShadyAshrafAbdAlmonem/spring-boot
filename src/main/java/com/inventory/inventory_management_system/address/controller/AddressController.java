package com.inventory.inventory_management_system.address.controller;

import com.inventory.inventory_management_system.address.dto.request.CreateAddressRequest;
import com.inventory.inventory_management_system.address.dto.request.UpdateAddressRequest;
import com.inventory.inventory_management_system.address.dto.response.AddressResponse;
import com.inventory.inventory_management_system.address.service.AddressService;
import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> create(@Valid @RequestBody @NonNull CreateAddressRequest request) {
        AddressResponse created = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Address created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(ApiResponse.success(addressService.getAddressById(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(addressService.getAllAddresses()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AddressResponse>> update(
            @PathVariable @NonNull Long id, 
            @Valid @RequestBody @NonNull UpdateAddressRequest request) {
        AddressResponse updated = addressService.updateAddress(id, request);
        return ResponseEntity.ok(ApiResponse.success(updated, "Address updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @NonNull Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Address deleted successfully"));
    }
}