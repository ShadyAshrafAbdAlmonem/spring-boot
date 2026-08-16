package com.inventory.inventory_management_system.address.service;

import com.inventory.inventory_management_system.address.dto.request.CreateAddressRequest;
import com.inventory.inventory_management_system.address.dto.request.UpdateAddressRequest;
import com.inventory.inventory_management_system.address.dto.response.AddressResponse;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AddressService {
    @NonNull AddressResponse createAddress(@NonNull CreateAddressRequest request);
    @NonNull AddressResponse getAddressById(@NonNull Long id);
    @NonNull List<AddressResponse> getAllAddresses();
    @NonNull AddressResponse updateAddress(@NonNull Long id, @NonNull UpdateAddressRequest request);
    void deleteAddress(@NonNull Long id);
}