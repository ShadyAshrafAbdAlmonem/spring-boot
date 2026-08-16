package com.inventory.inventory_management_system.brand.controller;

import com.inventory.inventory_management_system.brand.dto.request.CreateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.request.UpdateBrandRequest;
import com.inventory.inventory_management_system.brand.dto.response.BrandResponse;
import com.inventory.inventory_management_system.brand.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<BrandResponse> create(@Valid @RequestBody @NonNull CreateBrandRequest request) {
        return new ResponseEntity<>(brandService.createBrand(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @GetMapping
    public ResponseEntity<Page<BrandResponse>> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        return ResponseEntity.ok(brandService.searchBrands(name, active, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> update(@PathVariable @NonNull Long id, @RequestBody @NonNull UpdateBrandRequest request) {
        return ResponseEntity.ok(brandService.updateBrand(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @NonNull Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
