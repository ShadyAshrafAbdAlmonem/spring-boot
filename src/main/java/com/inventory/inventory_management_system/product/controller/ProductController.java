package com.inventory.inventory_management_system.product.controller;

import com.inventory.inventory_management_system.common.constant.ApiPaths;
import com.inventory.inventory_management_system.common.response.ApiResponse;
import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.product.dto.request.*;
import com.inventory.inventory_management_system.product.dto.response.*;
import com.inventory.inventory_management_system.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.lang.NonNull;

@RestController
@RequestMapping(ApiPaths.BASE_PATH + "/products")
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> createProduct(@Valid @RequestBody @NonNull CreateProductRequest request) {
        ProductDetailsResponse created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Product created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllProducts(
            @ModelAttribute ProductFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(productService.getAllProducts(filter, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> getProductById(@PathVariable @NonNull Long id) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductById(id)));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> getProductBySku(@PathVariable String sku) {
        return ResponseEntity.ok(ApiResponse.success(productService.getProductBySku(sku)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> updateProduct(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, request), "Product updated successfully"));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductDetailsResponse>> updateStock(
            @PathVariable @NonNull Long id,
            @Valid @RequestBody @NonNull UpdateStockRequest request) {
        return ResponseEntity.ok(ApiResponse.success(productService.updateStock(id, request), "Stock updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable @NonNull Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
    }
}
