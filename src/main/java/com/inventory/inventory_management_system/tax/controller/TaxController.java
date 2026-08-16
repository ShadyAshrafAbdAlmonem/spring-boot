package com.inventory.inventory_management_system.tax.controller;

import com.inventory.inventory_management_system.tax.dto.request.TaxRequest;
import com.inventory.inventory_management_system.tax.dto.response.TaxResponse;
import com.inventory.inventory_management_system.tax.service.TaxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/taxes")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping
    public ResponseEntity<TaxResponse> create(@Valid @RequestBody @NonNull TaxRequest request) {
        return new ResponseEntity<>(taxService.createTax(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(taxService.getTaxById(id));
    }

    @GetMapping
    public ResponseEntity<List<TaxResponse>> getAll() {
        return ResponseEntity.ok(taxService.getAllTaxes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxResponse> update(@PathVariable @NonNull Long id, @Valid @RequestBody @NonNull TaxRequest request) {
        return ResponseEntity.ok(taxService.updateTax(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        taxService.deleteTax(id);
        return ResponseEntity.noContent().build();
    }
}
