package com.inventory.inventory_management_system.address.validator;

import com.inventory.inventory_management_system.address.dto.request.CreateAddressRequest;
import com.inventory.inventory_management_system.address.dto.request.UpdateAddressRequest;
import com.inventory.inventory_management_system.address.entity.Address;
import com.inventory.inventory_management_system.common.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class AddressValidator {

    private static final int ZIP_CODE_MAX_LENGTH = 20;
    private static final int STREET_MAX_LENGTH = 500;

    public void validateCreate(CreateAddressRequest request) {
        validateStreetLength(request.getStreet());
        validateZipCode(request.getZipCode());
        validateRequiredFields(request.getCity(), request.getCountry());
    }

    public void validateUpdate(Address existingAddress, UpdateAddressRequest request) {
        if (existingAddress == null) {
            throw new BadRequestException("Address not found");
        }

        if (request.getStreet() != null) {
            validateStreetLength(request.getStreet());
        }

        if (request.getZipCode() != null) {
            validateZipCode(request.getZipCode());
        }
    }

    private void validateStreetLength(String street) {
        if (street != null && street.length() > STREET_MAX_LENGTH) {
            throw new BadRequestException("Street address must not exceed " + STREET_MAX_LENGTH + " characters");
        }
    }

    private void validateZipCode(String zipCode) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            throw new BadRequestException("Zip code is required");
        }
        if (zipCode.length() > ZIP_CODE_MAX_LENGTH) {
            throw new BadRequestException("Zip code must not exceed " + ZIP_CODE_MAX_LENGTH + " characters");
        }
        if (!zipCode.matches("^[0-9A-Za-z\\-\\s]+$")) {
            throw new BadRequestException("Zip code contains invalid characters");
        }
    }

    private void validateRequiredFields(String city, String country) {
        if (city == null || city.trim().isEmpty()) {
            throw new BadRequestException("City is required");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new BadRequestException("Country is required");
        }
    }
}
