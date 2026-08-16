package com.inventory.inventory_management_system.address.service.impl;

import com.inventory.inventory_management_system.address.dto.request.CreateAddressRequest;
import com.inventory.inventory_management_system.address.dto.request.UpdateAddressRequest;
import com.inventory.inventory_management_system.address.dto.response.AddressResponse;
import com.inventory.inventory_management_system.address.entity.Address;
import com.inventory.inventory_management_system.address.mapper.AddressMapper;
import com.inventory.inventory_management_system.address.repository.AddressRepository;
import com.inventory.inventory_management_system.address.service.AddressService;
import com.inventory.inventory_management_system.address.validator.AddressValidator;
import com.inventory.inventory_management_system.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final AddressValidator addressValidator;

    @Override
    public @NonNull AddressResponse createAddress(@NonNull CreateAddressRequest request) {
        addressValidator.validateCreate(request);
        Address address = addressMapper.toEntity(request);
        Address saved = Objects.requireNonNull(addressRepository.save(address), "Saved address must not be null");
        return addressMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull AddressResponse getAddressById(@NonNull Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
        return addressMapper.toResponse(address);
    }

    @Override
    @Transactional(readOnly = true)
    public @NonNull List<AddressResponse> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toResponse)
                .toList();
    }

    @Override
    public @NonNull AddressResponse updateAddress(@NonNull Long id, @NonNull UpdateAddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));

        addressValidator.validateUpdate(address, request);
        addressMapper.updateEntityFromDto(request, address);
        
        return addressMapper.toResponse(Objects.requireNonNull(addressRepository.save(address), "Saved address must not be null"));
    }

    @Override
    public void deleteAddress(@NonNull Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found with id: " + id);
        }
        addressRepository.deleteById(id);
    }
}