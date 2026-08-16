package com.inventory.inventory_management_system.currency.controller;

import com.inventory.inventory_management_system.currency.dto.request.CurrencyRequest;
import com.inventory.inventory_management_system.currency.dto.response.CurrencyResponse;
import com.inventory.inventory_management_system.currency.service.CurrencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/currencies")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping
    public ResponseEntity<CurrencyResponse> create(@Valid @RequestBody @NonNull CurrencyRequest request) {
        return new ResponseEntity<>(currencyService.createCurrency(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(currencyService.getCurrencyById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CurrencyResponse> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(currencyService.getCurrencyByCode(code));
    }

    @GetMapping
    public ResponseEntity<List<CurrencyResponse>> getAll() {
        return ResponseEntity.ok(currencyService.getAllCurrencies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurrencyResponse> update(@PathVariable @NonNull Long id, @Valid @RequestBody @NonNull CurrencyRequest request) {
        return ResponseEntity.ok(currencyService.updateCurrency(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        currencyService.deleteCurrency(id);
        return ResponseEntity.noContent().build();
    }
}
