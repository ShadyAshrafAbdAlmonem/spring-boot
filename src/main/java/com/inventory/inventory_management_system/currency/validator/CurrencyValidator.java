package com.inventory.inventory_management_system.currency.validator;

import com.inventory.inventory_management_system.currency.dto.request.CurrencyRequest;
import com.inventory.inventory_management_system.currency.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyValidator {

    private final CurrencyRepository currencyRepository;

    public void validateCreate(CurrencyRequest request) {
        if (currencyRepository.existsByCode(request.getCode().toUpperCase())) {
            throw new IllegalArgumentException("Currency code already exists: " + request.getCode());
        }
    }

    public void validateUpdate(Long id, CurrencyRequest request) {
        currencyRepository.findByCode(request.getCode().toUpperCase())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new IllegalArgumentException("Currency code already in use by another record: " + request.getCode());
                    }
                });
    }
}