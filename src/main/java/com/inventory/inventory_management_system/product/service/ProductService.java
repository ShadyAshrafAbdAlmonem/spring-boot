package com.inventory.inventory_management_system.product.service;

import com.inventory.inventory_management_system.common.response.PageResponse;
import com.inventory.inventory_management_system.product.dto.request.CreateProductRequest;
import com.inventory.inventory_management_system.product.dto.request.ProductFilterRequest;
import com.inventory.inventory_management_system.product.dto.request.UpdateProductRequest;
import com.inventory.inventory_management_system.product.dto.request.UpdateStockRequest;
import com.inventory.inventory_management_system.product.dto.response.ProductDetailsResponse;
import com.inventory.inventory_management_system.product.dto.response.ProductResponse;
import org.springframework.lang.NonNull;

public interface ProductService {
    ProductDetailsResponse createProduct(@NonNull CreateProductRequest request);
    ProductDetailsResponse updateProduct(@NonNull Long id, @NonNull UpdateProductRequest request);
    ProductDetailsResponse updateStock(@NonNull Long id, @NonNull UpdateStockRequest request);
    ProductDetailsResponse getProductById(@NonNull Long id);
    ProductDetailsResponse getProductBySku(@NonNull String sku);
    PageResponse<ProductResponse> getAllProducts(@NonNull ProductFilterRequest filter, int page, int size);
    void deleteProduct(@NonNull Long id);
}
