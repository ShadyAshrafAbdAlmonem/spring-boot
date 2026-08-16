package com.inventory.inventory_management_system.currency.service.impl;

import com.inventory.inventory_management_system.currency.dto.request.CurrencyRequest;
import com.inventory.inventory_management_system.currency.dto.response.CurrencyResponse;
import com.inventory.inventory_management_system.currency.entity.Currency;
import com.inventory.inventory_management_system.currency.mapper.CurrencyMapper;
import com.inventory.inventory_management_system.currency.repository.CurrencyRepository;
import com.inventory.inventory_management_system.currency.service.CurrencyService;
import com.inventory.inventory_management_system.currency.validator.CurrencyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@SuppressWarnings("null")
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private final CurrencyValidator currencyValidator;

    @Override
    public CurrencyResponse createCurrency(@NonNull CurrencyRequest request) {
        currencyValidator.validateCreate(request);

        Currency currency = currencyMapper.toEntity(request);
        currency.setCode(currency.getCode().toUpperCase());

        if (Boolean.TRUE.equals(request.getIsDefault())) {
            unsetPreviousDefault();
        }

        return currencyMapper.toResponse(currencyRepository.save(currency));
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponse getCurrencyById(@NonNull Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));
        return currencyMapper.toResponse(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public CurrencyResponse getCurrencyByCode(String code) {
        Currency currency = currencyRepository.findByCode(code.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Currency not found with code: " + code));
        return currencyMapper.toResponse(currency);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::toResponse)
                .toList();
    }

    @Override
    public CurrencyResponse updateCurrency(@NonNull Long id, @NonNull CurrencyRequest request) {
        currencyValidator.validateUpdate(id, request);

        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));

        if (Boolean.TRUE.equals(request.getIsDefault()) && !Boolean.TRUE.equals(currency.getIsDefault())) {
            unsetPreviousDefault();
        }

        currencyMapper.updateEntityFromDto(request, currency);
        currency.setCode(currency.getCode().toUpperCase());

        return currencyMapper.toResponse(currencyRepository.save(currency));
    }

    @Override
    public void deleteCurrency(@NonNull Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Currency not found with id: " + id));

        if (Boolean.TRUE.equals(currency.getIsDefault())) {
            throw new IllegalStateException("Cannot delete the default system currency");
        }

        currencyRepository.delete(currency);
    }

    private void unsetPreviousDefault() {
        currencyRepository.findByIsDefaultTrue().ifPresent(curr -> {
            curr.setIsDefault(false);
            currencyRepository.save(curr);
        });
    }
}
