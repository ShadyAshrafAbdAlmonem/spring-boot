package com.inventory.inventory_management_system.discount.controller;

import com.inventory.inventory_management_system.discount.dto.request.ApplyDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.CreateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.request.UpdateDiscountRequest;
import com.inventory.inventory_management_system.discount.dto.response.DiscountDetailsResponse;
import com.inventory.inventory_management_system.discount.dto.response.DiscountResponse;
import com.inventory.inventory_management_system.discount.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountResponse> create(@Valid @RequestBody @NonNull CreateDiscountRequest request) {
        return new ResponseEntity<>(discountService.createDiscount(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(discountService.getDiscountById(id));
    }

    @GetMapping
    public ResponseEntity<Page<DiscountResponse>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        return ResponseEntity.ok(discountService.getDiscounts(name, active, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountResponse> update(@PathVariable @NonNull Long id, @RequestBody @NonNull UpdateDiscountRequest request) {
        return ResponseEntity.ok(discountService.updateDiscount(id, request));
    }

    @PostMapping("/calculate")
    public ResponseEntity<DiscountDetailsResponse> calculate(@Valid @RequestBody @NonNull ApplyDiscountRequest request) {
        return ResponseEntity.ok(discountService.calculateDiscount(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        discountService.deleteDiscount(id);
        return ResponseEntity.noContent().build();
    }
}
