package com.inventory.inventory_management_system.currency.service;

import com.inventory.inventory_management_system.currency.dto.request.CurrencyRequest;
import com.inventory.inventory_management_system.currency.dto.response.CurrencyResponse;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CurrencyService {
    CurrencyResponse createCurrency(@NonNull CurrencyRequest request);
    CurrencyResponse getCurrencyById(@NonNull Long id);
    CurrencyResponse getCurrencyByCode(@NonNull String code);
    List<CurrencyResponse> getAllCurrencies();
    CurrencyResponse updateCurrency(@NonNull Long id, @NonNull CurrencyRequest request);
    void deleteCurrency(@NonNull Long id);
}
